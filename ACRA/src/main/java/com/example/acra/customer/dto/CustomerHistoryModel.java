package com.example.acra.customer.dto;

import com.example.acra.customer.entity.CreditEntity;
import com.example.acra.customer.entity.CustomerHistoryEntity;
import com.example.acra.customer.requests.creating_requests.CreditRequest;
import com.example.acra.customer.requests.creating_requests.CustomerHistoryRequest;
import com.example.acra.customer.response.CreditResponse;
import com.example.acra.customer.response.CustomerHistoryResponse;

import java.util.ArrayList;
import java.util.List;

public class CustomerHistoryModel {

    private String salary;
    private Boolean hasActiveCredit;
    private Short creditScore;
    private List<CreditModel> creditModels;


    public CustomerHistoryModel(final String salary, final Boolean hasActiveCredit,
                                final Short creditScore, final List<CreditModel> creditModels) {

        this.salary = salary;
        this.hasActiveCredit = hasActiveCredit;
        this.creditScore = creditScore;
        this.creditModels = creditModels;

    }
    public CustomerHistoryModel(final CustomerHistoryEntity customerHistoryEntity) {

        this.salary = customerHistoryEntity.getSalary();
        this.hasActiveCredit = customerHistoryEntity.getHasActiveCredit();
        this.creditScore = customerHistoryEntity.getCreditScore();
        this.creditModels = customerHistoryEntity.getCredits().stream().map(CreditModel::new).toList();

    }

    public CustomerHistoryModel(final CustomerHistoryRequest customerHistoryRequest) {
        this.creditModels = castFromRequestToModel(customerHistoryRequest.creditRequest());
        this.hasActiveCredit = Boolean.valueOf(customerHistoryRequest.hasActiveCredit());
        this.salary = customerHistoryRequest.salary();
        this.creditScore = Short.valueOf(customerHistoryRequest.creditScore());
    }
    public CustomerHistoryModel(final CustomerHistoryResponse customerHistoryRequest) {
        this.creditModels = castFromResponseToModel(customerHistoryRequest.creditResponse());
        this.hasActiveCredit = Boolean.valueOf(customerHistoryRequest.hasActiveCredit());
        this.salary = customerHistoryRequest.salary();
        this.creditScore = Short.valueOf(customerHistoryRequest.creditScore());
    }




    public String getSalary() {
        return salary;
    }

    public void setSalary(final String salary) {
        this.salary = salary;
    }

    public Boolean getHasActiveCredit() {
        return hasActiveCredit;
    }

    public List<CreditModel> getCreditModels() {
        return creditModels;
    }

    public void setCreditModels(List<CreditModel> creditModels) {
        this.creditModels = creditModels;
    }

    public void setHasActiveCredit(final Boolean hasActiveCredit) {
        this.hasActiveCredit = hasActiveCredit;
    }

    public Short getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(final short creditScore) {
        this.creditScore = creditScore;
    }

    private List<CreditModel> castFromEntityToModel(final List<CreditEntity> creditEntities) {
        List<CreditModel> creditModels = new ArrayList<>();
        creditEntities.forEach( creditEntity -> creditModels.add(new CreditModel(creditEntity)));
        return creditModels;

    }

    private List<CreditModel> castFromRequestToModel(final List<CreditRequest> creditRequests) {
        List<CreditModel> creditModels = new ArrayList<>();
        creditRequests.forEach(creditRequest -> creditModels.add(new CreditModel(creditRequest)));
        return  creditModels;
    }
    private List<CreditModel> castFromResponseToModel(final List<CreditResponse> creditResponses) {
        List<CreditModel> creditModels = new ArrayList<>();
        creditResponses.forEach(creditRequest -> creditModels.add(new CreditModel(creditRequest)));
        return  creditModels;
    }
}
