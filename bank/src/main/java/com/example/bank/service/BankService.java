package com.example.bank.service;

import com.example.bank.bank_entity.BankEntity;
import com.example.bank.customer.dto.*;
import com.example.bank.customer.entity.*;
import com.example.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class that provides banking operations related to customers and their information.
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
    private final ResultCustomerInfoRepository resultCustomerInfoRepository;

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
                       final PassportRepository passportRepository,
                       final ResultCustomerInfoRepository resultCustomerInfoRepository){

        this.bankRepository = bankRepository;
        this.creditRepository = creditRepository;
        this.customerRepository = customerRepository;
        this.workingPlaceRepository = workingPlaceRepository;
        this.customerHistoryRepository = customerHistoryRepository;
        this.addressRepository = addressRepository;
        this.passportRepository = passportRepository;
        this.resultCustomerInfoRepository = resultCustomerInfoRepository;

    }

    public void saveBank(final BankEntity bank) {
        bankRepository.save(bank);
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
     * Saves customer information by creating a new customer record or updating an existing customer's credit.
     *
     * @param addressModel      The address model containing the customer's address information.
     * @param passportModel     The passport model containing the customer's passport information.
     * @param customerInfoModel The customer info model containing the customer's general information.
     * @param customerHistory   The customer history model containing the customer's history information.
     * @param workingPlaceModel The working place model containing the customer's working place information.
     * @return {@code true} if the customer information is successfully saved or updated, {@code false} otherwise.
     */
    public boolean saveAcceptedCustomerCredit(final AddressModel addressModel,
                                              final PassportModel passportModel,
                                              final CustomerInfoModel customerInfoModel,
                                              final CustomerHistoryModel customerHistory,
                                              final WorkingPlaceModel workingPlaceModel) {

        Optional<CustomerEntity> customerEntity = getCustomer(passportModel.getPassportNumber());
        if (customerEntity.isPresent()) {
            customerEntity.get().getCustomerHistory().setCreditScore(customerHistory.getCreditScore());
            customerEntity.get().getCustomerHistory().setHasActiveCredit(true);
            customerHistoryRepository.save(customerEntity.get().getCustomerHistory());
            return addNewAcceptedCredit(customerHistory.getCreditModels().get(customerHistory.getCreditModels().size() - 1), customerEntity.get());
        }
        return saveAllEntities(new AddressEntity(addressModel), new PassportEntity(passportModel),
                new WorkingPlaceEntity(workingPlaceModel), new CustomerHistoryEntity(customerHistory),
                customerInfoModel, mapToCreditEntity(customerHistory.getCreditModels()));

    }

    public boolean saveNotAcceptedCustomerCredit(final AddressModel addressModel,
                                                 final PassportModel passportModel,
                                                 final CustomerInfoModel customerInfoModel,
                                                 final CustomerHistoryModel customerHistory,
                                                 final WorkingPlaceModel workingPlaceModel) {
        Optional<CustomerEntity> customerEntity = getCustomer(passportModel.getPassportNumber());
        if (customerEntity.isPresent()) {
            customerEntity.get().getCustomerHistory().setCreditScore(customerHistory.getCreditScore());
            customerHistoryRepository.save(customerEntity.get().getCustomerHistory());
            return addRejectedCredit(customerHistory.getCreditModels().get(customerHistory.getCreditModels().size() - 1), customerEntity.get());
        }
        return saveAllEntities(new AddressEntity(addressModel), new PassportEntity(passportModel),
                new WorkingPlaceEntity(workingPlaceModel), new CustomerHistoryEntity(customerHistory),
                customerInfoModel, mapToCreditEntity(customerHistory.getCreditModels()));

    }

    /**
     * Adds a new credit for a customer identified by the provided passport number.
     *
     * @param creditModel    The model object containing the details of the new credit.
     * @param customerEntity The passport number of the customer.
     * @return true if the credit is successfully added for the customer, false otherwise.
     */
    public boolean addNewAcceptedCredit(final CreditModel creditModel, final CustomerEntity customerEntity) {
        final CreditEntity creditEntity = new CreditEntity(creditModel);
        creditEntity.setCustomerHistoryEntity(customerEntity.getCustomerHistory());
        creditRepository.save(creditEntity);
        return true;
    }

    public boolean addRejectedCredit(final CreditModel creditModel, final CustomerEntity customerEntity) {
        final CreditEntity creditEntity = new CreditEntity(creditModel);
        creditEntity.setCustomerHistoryEntity(customerEntity.getCustomerHistory());
        creditRepository.save(creditEntity);
        return true;
    }


    public void saveResultOfCustomer(final ResultCustomerInfoModel resultCustomerInfoModel) {
        resultCustomerInfoRepository.save(new ResultCustomerInfoEntity(resultCustomerInfoModel));
    }

    /**
     * Retrieves a customer entity from the customer repository based on the provided passport number.
     *
     * @param passportNumber The passport number of the customer.
     * @return An optional containing the customer entity if found, or an empty optional otherwise.
     */
    private Optional<CustomerEntity> getCustomer(final String passportNumber) {
        return customerRepository.findCustomerEntityByPassport_PassportNumber(passportNumber);
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
