package com.example.bank.service;

import com.example.bank.customer.dto.*;
import com.example.bank.customer.entity.*;
import com.example.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final CreditRepository creditRepository;
    private final WorkingPlaceRepository workingPlaceRepository;
    private final AddressRepository addressRepository;
    private final CustomerHistoryRepository customerHistoryRepository;
    private final PassportRepository passportRepository;

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

    public CustomerModel newCredit(final CustomerModel customerModel) {
        return new CustomerModel(new CustomerEntity());
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
