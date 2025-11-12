package com.upb.agripos.model.pembayaran;

import com.upb.agripos.model.kontrak.Validatable;
import com.upb.agripos.model.kontrak.Receiptable;

public class TransferBank extends Pembayaran implements Validatable, Receiptable {
    private String bankTujuan;
    private String otp;

    public TransferBank(String invoiceNo, double total, String bankTujuan, String otp) {
        super(invoiceNo, total);
        this.bankTujuan = bankTujuan;
        this.otp = otp;
    }

    @Override
    public double biaya() {
        return 3500; // biaya tetap
    }

    @Override
    public boolean validasi() {
        return otp != null && otp.matches("\\d{6}");
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | TOTAL+BIAYA: %.2f | BANK: %s | STATUS: %s",
            invoiceNo, totalBayar(), bankTujuan, prosesPembayaran() ? "BERHASIL" : "GAGAL"
        );
    }
}
