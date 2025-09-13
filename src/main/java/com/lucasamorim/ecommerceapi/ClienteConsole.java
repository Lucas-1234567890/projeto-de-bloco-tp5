package com.lucasamorim.ecommerceapi;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucasamorim.ecommerceapi.model.Cliente;
import com.lucasamorim.ecommerceapi.repository.ClienteRepository;

@Component
public class ClienteConsole {

    @Autowired
    private ClienteRepository clienteRepository;

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== CRUD CLIENTES ===");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("3 - Atualizar Cliente");
            System.out.println("4 - Deletar Cliente");
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
        Cliente c = new Cliente();
        System.out.print("Nome: ");
        c.setNome(sc.nextLine());
        System.out.print("Email: ");
        c.setEmail(sc.nextLine());
        System.out.print("Senha: ");
        c.setSenha(sc.nextLine());
        System.out.print("Telefone: ");
        c.setTelefone(sc.nextLine());

        clienteRepository.save(c);
        System.out.println("Cliente cadastrado!");
    }

    private void listar() {
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(c -> System.out.println(
                    c.getId() + " - " + c.getNome() + " | " + c.getEmail() + " | Tel: " + c.getTelefone()));
        }
    }

    private void atualizar(Scanner sc) {
        listar();
        System.out.print("ID do cliente para atualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Cliente c = clienteRepository.findById(id).orElse(null);
        if (c == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Novo nome (" + c.getNome() + "): ");
        c.setNome(sc.nextLine());
        System.out.print("Novo email (" + c.getEmail() + "): ");
        c.setEmail(sc.nextLine());
        System.out.print("Nova senha (" + c.getSenha() + "): ");
        c.setSenha(sc.nextLine());
        System.out.print("Novo telefone (" + c.getTelefone() + "): ");
        c.setTelefone(sc.nextLine());

        clienteRepository.save(c);
        System.out.println("Cliente atualizado!");
    }

    private void deletar(Scanner sc) {
        listar();
        System.out.print("ID do cliente para deletar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        clienteRepository.deleteById(id);
        System.out.println("Cliente removido!");
    }
}