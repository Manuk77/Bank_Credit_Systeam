package com.example.bank.service;

import com.example.bank.customer.dto.*;
import com.example.bank.customer.entity.*;
import com.example.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class that provides banking operations related to customers and their information.
 *
 * This class is responsible for managing customer information, including retrieval, saving, and credit operations.
 * It interacts with various repositories to persist and retrieve data related to customers, addresses, passports,
 * working places, customer histories, and credits.
 */
@Service
public class BankService {

    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final CreditRepository creditRepository;
    private final WorkingPlaceRepository workingPlaceRepository;
    private final AddressRepository addressRepository;
    private final CustomerHistoryRepository customerHistoryRepository;
    private final PassportRepository passportRepository;

    /**
     * Constructs a new instance of the BankService class.
     *
     * @param bankRepository              The repository for accessing and managing bank data.
     * @param customerRepository          The repository for accessing and managing customer data.
     * @param workingPlaceRepository      The repository for accessing and managing working place data.
     * @param creditRepository            The repository for accessing and managing credit data.
     * @param addressRepository           The repository for accessing and managing address data.
     * @param customerHistoryRepository   The repository for accessing and managing customer history data.
     * @param passportRepository          The repository for accessing and managing passport data.
     */
    @Autowired
    public BankService(final BankRepository bankRepository,
                       final CustomerRepository customerRepository,
                       final WorkingPlaceRepository workingPlaceRepository,
                       final CreditRepository creditRepository,
                       final AddressRepository addressRepository,
                       final CustomerHistoryRepository customerHistoryRepository,
                       final PassportRepository passportRepository){

        this.bankRepository = bankRepository;
        this.creditRepository = creditRepository;
        this.customerRepository = customerRepository;
        this.workingPlaceRepository = workingPlaceRepository;
        this.customerHistoryRepository = customerHistoryRepository;
        this.addressRepository = addressRepository;
        this.passportRepository = passportRepository;

    }

    /**
     * Retrieves customer information based on the provided passport number.
     *
     * @param passportNumber The passport number of the customer.
     * @return A CustomerModel object representing the customer information, or null if no customer is found with the given passport number.
     */
    public CustomerModel getInfo(final String passportNumber) {
        Optional<CustomerEntity> optionalCustomerEntity =
                customerRepository.findCustomerEntityByPassport_PassportNumber(passportNumber);
        // custom exception is going to be added here
        return optionalCustomerEntity.map(CustomerModel::new).orElse(null);

    }

    /**
     * Saves customer information by creating and persisting corresponding entities.
     *
     * @param addressModel     The model object containing the address information of the customer.
     * @param passportModel    The model object containing the passport information of the customer.
     * @param customerInfoModel The model object containing general information about the customer.
     * @param customerHistory  The model object containing the historical information of the customer.
     * @param workingPlaceModel The model object containing the working place information of the customer.
     * @return true if the customer information is successfully saved, false otherwise.
     */
    public boolean saveCustomer(final AddressModel addressModel,
                                final PassportModel passportModel,
                                final CustomerInfoModel customerInfoModel,
                                final CustomerHistoryModel customerHistory,
                                final WorkingPlaceModel workingPlaceModel) {

        return saveAllEntities(new AddressEntity(addressModel), new PassportEntity(passportModel ),
                new WorkingPlaceEntity(workingPlaceModel), new CustomerHistoryEntity(customerHistory),
                customerInfoModel, mapToCreditEntity(customerHistory.getCreditModels()));
    }

    /**
     * Adds a new credit for a customer identified by the provided passport number.
     *
     * @param creditModel    The model object containing the details of the new credit.
     * @param passportNumber The passport number of the customer.
     * @return true if the credit is successfully added for the customer, false otherwise.
     */
    public boolean addNewCredit(final CreditModel creditModel, final String passportNumber) {
        Optional<CustomerEntity> customerOp = customerRepository.findCustomerEntityByPassport_PassportNumber(passportNumber);
        if (!customerOp.isPresent())
            return false;
        CreditEntity creditEntity = new CreditEntity(creditModel);
        creditEntity.setCustomerHistoryEntity(customerOp.get().getCustomerHistory());
        creditRepository.save(creditEntity);
        return true;

    }


    /**
     * Saves all entities related to a customer, including address, passport, working place, customer history,
     * customer information, and credit entities.
     *
     * @param addressEntity      The entity representing the customer's address.
     * @param passportEntity     The entity representing the customer's passport.
     * @param workingPlaceEntity The entity representing the customer's working place.
     * @param customerHistory    The entity representing the customer's history.
     * @param customerInfoModel  The model object containing additional customer information.
     * @param creditEntities     The list of credit entities associated with the customer.
     * @return true if all entities are successfully saved, false otherwise.
     */
    private boolean saveAllEntities(final AddressEntity addressEntity, final PassportEntity passportEntity,
                                    final WorkingPlaceEntity workingPlaceEntity, final CustomerHistoryEntity customerHistory,
                                    final CustomerInfoModel customerInfoModel, final List<CreditEntity> creditEntities) {

        saveAddress(addressEntity);
        savePassport(passportEntity);
        saveWorkingPlace(workingPlaceEntity);
        saveCustomerHistory(customerHistory);
        creditRepository.saveAll(addCreditHistory(creditEntities, customerHistory));
        customerRepository.save(new CustomerEntity(passportEntity, customerHistory,
                addressEntity, workingPlaceEntity, customerInfoModel));

        return true;
    }

    private void saveAddress(final AddressEntity address) {
        addressRepository.save(address);
    }
    private void savePassport(final PassportEntity passport) {
        passportRepository.save(passport);
    }
    private void saveWorkingPlace(final WorkingPlaceEntity workingPlace) {
        workingPlaceRepository.save(workingPlace);
    }
    private void saveCustomerHistory(final CustomerHistoryEntity customerHistory) {
        customerHistoryRepository.save(customerHistory);
    }

    /**
     * Maps a list of credit models to a list of credit entities.
     *
     * @param creditModels The list of credit models to be mapped.
     * @return The list of credit entities mapped from the credit models.
     */
    private List<CreditEntity> mapToCreditEntity(final List<CreditModel> creditModels) {
        List<CreditEntity> creditEntities = new ArrayList<>();
        creditModels.forEach(creditModel -> creditEntities.add(new CreditEntity(creditModel)));
        return  creditEntities;
    }

    /**
     * Adds a customer history entity to each credit entity in the list.
     *
     * @param creditEntities The list of credit entities to add the customer history entity to.
     * @param entity The customer history entity to be added.
     * @return The modified list of credit entities with the customer history entity added.
     */
    private List<CreditEntity> addCreditHistory( List<CreditEntity> creditEntities, final CustomerHistoryEntity entity) {
        for (final CreditEntity c : creditEntities) {
            c.setCustomerHistoryEntity(entity);
        }
        return creditEntities;
    }
}
