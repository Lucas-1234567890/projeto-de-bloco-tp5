package com.lucasamorim.ecommerceapi.service;

import org.springframework.stereotype.Service;
import com.lucasamorim.ecommerceapi.model.NotaFiscal;
import com.lucasamorim.ecommerceapi.model.Pedido;
import com.lucasamorim.ecommerceapi.repository.NotaFiscalRepository;
import java.time.LocalDateTime;

@Service
public class NotaFiscalService {
    private final NotaFiscalRepository notaRepo;

    public NotaFiscalService(NotaFiscalRepository notaRepo) {
        this.notaRepo = notaRepo;
    }

    public NotaFiscal gerarNota(Pedido pedido) {
        NotaFiscal nf = new NotaFiscal();
        nf.setPedido(pedido);
        nf.setNumero("NFE" + pedido.getId());
        nf.setDataEmissao(LocalDateTime.now());
        return notaRepo.save(nf);
    }

    public NotaFiscal buscarPorId(Long id) {
        return notaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota Fiscal não encontrada"));
    }
}
