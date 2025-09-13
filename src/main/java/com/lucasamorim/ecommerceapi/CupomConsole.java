package com.lucasamorim.ecommerceapi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucasamorim.ecommerceapi.model.Cupom;
import com.lucasamorim.ecommerceapi.repository.CupomRepository;

@Component
public class CupomConsole {

    @Autowired
    private CupomRepository cupomRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== CRUD CUPONS ===");
            System.out.println("1 - Cadastrar Cupom");
            System.out.println("2 - Listar Cupons");
            System.out.println("3 - Atualizar Cupom");
            System.out.println("4 - Deletar Cupom");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpa buffer

            switch (opcao) {
                case 1 -> cadastrar(sc);
                case 2 -> listar();
                case 3 -> atualizar(sc);
                case 4 -> deletar(sc);
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar(Scanner sc) {
        Cupom c = new Cupom();
        System.out.print("Código: ");
        c.setCodigo(sc.nextLine());
        System.out.print("Desconto: ");
        c.setDesconto(sc.nextDouble());
        sc.nextLine(); // limpa buffer
        System.out.print("Validade (yyyy-MM-dd HH:mm): ");
        c.setValidade(LocalDateTime.parse(sc.nextLine(), formatter));

        cupomRepository.save(c);
        System.out.println("Cupom cadastrado!");
    }

    private void listar() {
        List<Cupom> cupons = cupomRepository.findAll();
        cupons.forEach(c -> System.out.println(
            c.getId() + " - " + c.getCodigo() + " | " + c.getDesconto() + "% | " + c.getValidade().format(formatter)
        ));
    }

    private void atualizar(Scanner sc) {
        listar();
        System.out.print("ID do cupom para atualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Cupom c = cupomRepository.findById(id).orElse(null);
        if (c == null) {
            System.out.println("Cupom não encontrado!");
            return;
        }

        System.out.print("Novo código (" + c.getCodigo() + "): ");
        c.setCodigo(sc.nextLine());
        System.out.print("Novo desconto (" + c.getDesconto() + "): ");
        c.setDesconto(sc.nextDouble());
        sc.nextLine();
        System.out.print("Nova validade (" + c.getValidade().format(formatter) + "): ");
        c.setValidade(LocalDateTime.parse(sc.nextLine(), formatter));

        cupomRepository.save(c);
        System.out.println("Cupom atualizado!");
    }

    private void deletar(Scanner sc) {
        listar();
        System.out.print("ID do cupom para deletar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        cupomRepository.deleteById(id);
        System.out.println("Cupom removido!");
    }
}
