package com.lucasamorim.ecommerceapi;


import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucasamorim.ecommerceapi.model.Produto;
import com.lucasamorim.ecommerceapi.repository.ProdutoRepository;

@Component
public class ProdutoConsole {

    @Autowired
    private ProdutoRepository produtoRepository;

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== CRUD PRODUTOS ===");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Atualizar Produto");
            System.out.println("4 - Deletar Produto");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

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
        Produto p = new Produto();
        System.out.print("Nome: ");
        p.setNome(sc.nextLine());
        System.out.print("Descrição: ");
        p.setDescricao(sc.nextLine());
        System.out.print("Preço: ");
        p.setPreco(sc.nextDouble());
        System.out.print("Quantidade: ");
        p.setQuantidadeEstoque(sc.nextInt());
        sc.nextLine();

        produtoRepository.save(p);
        System.out.println("Produto cadastrado com sucesso!");
    }

    private void listar() {
        List<Produto> produtos = produtoRepository.findAll();
        produtos.forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " (" + p.getPreco() + ")"));
    }

    private void atualizar(Scanner sc) {
        listar();
        System.out.print("ID do produto para atualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Produto p = produtoRepository.findById(id).orElse(null);
        if (p == null) {
            System.out.println("Produto não encontrado!");
            return;
        }

        System.out.print("Novo nome (" + p.getNome() + "): ");
        p.setNome(sc.nextLine());
        System.out.print("Nova descrição (" + p.getDescricao() + "): ");
        p.setDescricao(sc.nextLine());
        System.out.print("Novo preço (" + p.getPreco() + "): ");
        p.setPreco(sc.nextDouble());
        System.out.print("Nova quantidade (" + p.getQuantidadeEstoque() + "): ");
        p.setQuantidadeEstoque(sc.nextInt());
        sc.nextLine();

        produtoRepository.save(p);
        System.out.println("Produto atualizado!");
    }

    private void deletar(Scanner sc) {
        listar();
        System.out.print("ID do produto para deletar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        produtoRepository.deleteById(id);
        System.out.println("Produto removido!");
    }
}
