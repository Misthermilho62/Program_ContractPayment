package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService service;
	
	public ContractService(OnlinePaymentService service) {
		this.service = service;
	}

	public void processContract(Contract contract, int months) {
		double quota = contract.getTotalValue() / months;
		for (int i = 1; i <= months; i++) {
			
			LocalDate date = contract.getDate().plusMonths(i);
			double interestFee = service.interest(quota, i);
			double paymentFee = service.paymentFee(quota + interestFee);
			double totalQuota = quota + interestFee + paymentFee;
			
			contract.getInstallments().add(new Installment(date, totalQuota));
		}
	}
}
