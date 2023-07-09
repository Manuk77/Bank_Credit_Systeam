package com.example.acra.service;

import com.example.acra.customer.dto.*;
import com.example.acra.customer.entity.*;
import com.example.acra.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The `CustomerService` class provides business logic for managing customer information and operations.
 * It interacts with various repositories to perform CRUD operations on customer-related entities.
 */
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PassportRepository passportRepository;
    private final AddressRepository addressRepository;
    private final CreditRepository creditRepository;
    private final CustomerHistoryRepository customerHistoryRepository;
    private final WorkingPlaceRepository workingPlaceRepository;

    /**
     * Constructs a new instance of the `CustomerService` class.
     *
     * @param customerRepository         The repository for customer entities.
     * @param passportRepository         The repository for passport entities.
     * @param addressRepository          The repository for address entities.
     * @param creditRepository           The repository for credit entities.
     * @param customerHistoryRepository  The repository for customer history entities.
     * @param workingPlaceRepository     The repository for working place entities.
     */
    @Autowired
    public CustomerService(final CustomerRepository customerRepository,
                           final PassportRepository passportRepository,
                           final AddressRepository addressRepository,
                           final CreditRepository creditRepository,
                           final CustomerHistoryRepository customerHistoryRepository,
                           final WorkingPlaceRepository workingPlaceRepository) {
        this.customerRepository = customerRepository;
        this.creditRepository = creditRepository;
        this.passportRepository = passportRepository;
        this.addressRepository = addressRepository;
        this.customerHistoryRepository = customerHistoryRepository;
        this.workingPlaceRepository = workingPlaceRepository;
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
    public boolean saveNewCustomerOrUpdateCredit(final AddressModel addressModel,
                                                 final PassportModel passportModel,
                                                 final CustomerInfoModel customerInfoModel,
                                                 final CustomerHistoryModel customerHistory,
                                                 final WorkingPlaceModel workingPlaceModel) {

        Optional<CustomerEntity> customerEntity = getCustomer(passportModel.getPassportNumber());
            if (customerEntity.isEmpty())
                return saveAllEntities(new AddressEntity(addressModel), new PassportEntity(passportModel ),
                        new WorkingPlaceEntity(workingPlaceModel), new CustomerHistoryEntity(customerHistory),
                        customerInfoModel, castToCreditEntity(customerHistory.getCreditModels()));

        return addNewCredit(customerHistory.getCreditModels().get(customerHistory.getCreditModels().size() - 1), customerEntity.get());
    }

    /**
     * Adds a new credit to the customer with the specified passport number.
     *
     * @param creditModel     The credit model containing the details of the new credit.
     * @param customerEntity  The passport number of the customer.
     * @return `true` if the new credit is successfully added, `false` otherwise.
     */
    public Boolean addNewCredit(final CreditModel creditModel, final CustomerEntity customerEntity) {
        final CreditEntity creditEntity = new CreditEntity(creditModel);
        creditEntity.setCustomerHistoryEntity(customerEntity.getCustomerHistory());
        creditRepository.save(creditEntity);
        return true;
    }

    /**
     * Retrieves the customer information for the customer with the specified passport number.
     *
     * @param passportNumber The passport number of the customer.
     * @return The customer model containing the retrieved customer information.
     */
    public CustomerModel getInfo(final String passportNumber) {
        Optional<CustomerEntity> optionalCustomerEntity =
                customerRepository.findCustomerEntityByPassport_PassportNumber(passportNumber);
        // custom exception is going to be added here
        if (optionalCustomerEntity.isPresent()) {
            optionalCustomerEntity.get().getCustomerHistory().setCreditScore(
                    (short) (optionalCustomerEntity.get().getCustomerHistory().getCreditScore() - 1));
            customerHistoryRepository.save(optionalCustomerEntity.get().getCustomerHistory());
           return new CustomerModel(optionalCustomerEntity.get());
        }
        return null;
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
     * Retrieves the customer information for the customer with the specified first name and last name.
     *
     * @param firstName The first name of the customer.
     * @param lastName  The last name of the customer.
     * @return The customer model containing the retrieved customer information.
     */
    public CustomerModel getInfo(final String firstName, final String lastName) {
        Optional<CustomerEntity> optionalCustomerEntity =
                customerRepository.findCustomerEntityByPassport_FirstNameAndLastName(firstName, lastName);

        // custom exception is going to be added here
        if (optionalCustomerEntity.isPresent()) {
            optionalCustomerEntity.get().getCustomerHistory().setCreditScore(
                    (short) (optionalCustomerEntity.get().getCustomerHistory().getCreditScore() - 1));
            customerHistoryRepository.save(optionalCustomerEntity.get().getCustomerHistory());
            return new CustomerModel(optionalCustomerEntity.get());
        }
        return null;
    }


    /**
     * Saves all entities related to a customer by creating new records in the respective repositories.
     *
     * @param addressEntity      The address entity containing the customer's address information.
     * @param passportEntity     The passport entity containing the customer's passport information.
     * @param workingPlaceEntity The working place entity containing the customer's working place information.
     * @param customerHistory    The customer history entity containing the customer's history information.
     * @param customerInfoModel  The customer info model containing the customer's general information.
     * @param creditEntities     The list of credit entities associated with the customer.
     * @return `true` if all entities are successfully saved, `false` otherwise.
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

    private List<CreditEntity> castToCreditEntity(final List<CreditModel> creditModels) {
        List<CreditEntity> creditEntities = new ArrayList<>();
        creditModels.forEach(creditModel -> creditEntities.add(new CreditEntity(creditModel)));
        return  creditEntities;
    }
    private List<CreditEntity> addCreditHistory( List<CreditEntity> creditEntities, final CustomerHistoryEntity entity) {
        for (final CreditEntity c : creditEntities) {
            c.setCustomerHistoryEntity(entity);
        }
        return creditEntities;
    }
}
