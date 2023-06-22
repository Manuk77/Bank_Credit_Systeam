package com.example.acra.service;

import com.example.acra.customer.dto.*;
import com.example.acra.customer.entity.*;
import com.example.acra.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PassportRepository passportRepository;
    private final AddressRepository addressRepository;
    private final CreditRepository creditRepository;
    private final CustomerHistoryRepository customerHistoryRepository;
    private final WorkingPlaceRepository workingPlaceRepository;
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

    public boolean saveCustomer(final AddressModel addressModel,
                                final PassportModel passportModel,
                                final CustomerInfoModel customerInfoModel,
                                final CustomerHistoryModel customerHistory,
                                final WorkingPlaceModel workingPlaceModel) {

            return saveAllEntities(new AddressEntity(addressModel), new PassportEntity(passportModel ),
                    new WorkingPlaceEntity(workingPlaceModel), new CustomerHistoryEntity(customerHistory),
                    customerInfoModel, castToCreditEntity(customerHistory.getCreditModels()));

    }

    public Boolean updateCredit(final CreditModel creditModel, final  String passportNumber) {
        Optional<CustomerEntity> customerEntity =
                customerRepository.findCustomerEntityByPassport_PassportNumber(passportNumber);
        if (customerEntity.isEmpty())
            return false;
        CreditEntity creditEntity = new CreditEntity(creditModel);
        creditEntity.setCustomerHistoryEntity(customerEntity.get().getCustomerHistory());
        creditRepository.save(creditEntity);
        return true;
    }

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
