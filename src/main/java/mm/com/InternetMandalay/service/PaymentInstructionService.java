package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.PaymentInstruction;
import mm.com.InternetMandalay.request.PaymentInstructionUpdate;

public interface PaymentInstructionService {
    PaymentInstruction create();
    PaymentInstruction update(PaymentInstructionUpdate paymentInstructionUpdate);
    PaymentInstruction get();

}
