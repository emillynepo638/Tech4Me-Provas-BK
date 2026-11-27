package aplicativos;

import java.io.IOException;
import java.util.Scanner;

import classes.Pessoa;
import classes.Piloto;
import classes.Aeronave;

public class AppPilotos {

    public static void main(String[] args)
            throws InterruptedException, IOException {

        final int MAX_ELEMENTOS = 10;

        int opcao, qtdCadastrados = 0;

        Pessoa[] pilotos = new Pessoa[MAX_ELEMENTOS];

        Scanner in = new Scanner(System.in);

        do {

            System.out.println("\n****\nMENU\n****\n");
            System.out.println("1 - Cadastrar piloto");
            System.out.println("2 - Listar pilotos cadastrados");
            System.out.println("3 - Localizar piloto pelo CPF");
            System.out.println("4 - Cadastrar aeronave");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            opcao = in.nextInt();
            in.nextLine();

            // 1 - Cadastrar piloto
            if (opcao == 1) {

                if (qtdCadastrados == MAX_ELEMENTOS) {

                    System.out.println(
                        "\nNão há espaço para cadastrar novos pilotos."
                    );

                    voltarMenu(in);
                    continue;
                }

                System.out.println("\n*** CADASTRO DE PILOTO ***");

                Piloto piloto = new Piloto();

                System.out.print("Nome do piloto: ");
                piloto.setNome(in.nextLine());

                System.out.print("CPF do piloto: ");
                String cpf = in.nextLine();

                // Validação do CPF
                try {

                    piloto.setCpf(cpf);

                } catch (IllegalArgumentException e) {

                    System.out.println("\nErro: " + e.getMessage());

                    voltarMenu(in);
                    continue;
                }

                // Verificar se o CPF já está cadastrado
                if (localizarPiloto(pilotos, qtdCadastrados, cpf) != null) {

                    System.out.println("\nCPF já cadastrado!");

                    voltarMenu(in);
                    continue;
                }

                System.out.print("Brevê do piloto: ");
                piloto.setBreve(in.nextLine());

                pilotos[qtdCadastrados] = piloto;
                qtdCadastrados++;

                System.out.println("\nPiloto cadastrado com sucesso.");

                voltarMenu(in);

            // 2 - Listar pilotos
            } else if (opcao == 2) {

                if (qtdCadastrados == 0) {

                    System.out.println(
                        "\nNão há pilotos cadastrados para exibir."
                    );

                    voltarMenu(in);
                    continue;
                }

                System.out.println("\n*** PILOTOS CADASTRADOS ***");

                for (int i = 0; i < qtdCadastrados; i++) {

                    System.out.println("\nPiloto " + (i + 1));
                    System.out.println(pilotos[i].toString());
                    System.out.println("------------------------");
                }

                voltarMenu(in);

            // 3 - Localizar piloto
            } else if (opcao == 3) {

                System.out.println("\n*** LOCALIZAR PILOTO ***");

                System.out.print("Informe o CPF do piloto: ");
                String cpfBusca = in.nextLine();

                Piloto pilotoEncontrado = localizarPiloto(
                    pilotos, qtdCadastrados, cpfBusca
                );

                if (pilotoEncontrado != null) {

                    System.out.println("\nPiloto encontrado!");
                    System.out.println(pilotoEncontrado.toString());

                } else {

                    System.out.println("\nPiloto não encontrado.");
                }

                voltarMenu(in);

            // 4 - Cadastrar aeronave
            } else if (opcao == 4) {

                if (qtdCadastrados == 0) {

                    System.out.println(
                        "\nSem pilotos, não há como cadastrar uma aeronave"
                    );

                    voltarMenu(in);
                    continue;
                }

                System.out.println("\n*** CADASTRO DE AERONAVE ***");

                System.out.print("Informe o CPF do piloto: ");
                String cpfPiloto = in.nextLine();

                Piloto pilotoEncontrado = localizarPiloto(
                    pilotos, qtdCadastrados, cpfPiloto
                );

                if (pilotoEncontrado == null) {

                    System.out.println("\nPiloto não encontrado.");

                    voltarMenu(in);
                    continue;
                }

                if (!pilotoEncontrado.temEspacoAeronave()) {

                    System.out.println(
                        "\nO piloto atingiu o limite de aeronaves."
                    );

                    voltarMenu(in);
                    continue;
                }

                System.out.println(
                    "\nPiloto selecionado: " + pilotoEncontrado.getNome()
                );

                System.out.print("Modelo da aeronave: ");
                String modelo = in.nextLine();

                System.out.print("Número de série: ");
                String numeroSerie = in.nextLine();

                // Verificar se a aeronave já existe
                Aeronave aeronave = localizarAeronave(
                    pilotos, qtdCadastrados, numeroSerie
                );

                if (aeronave == null) {

                    aeronave = new Aeronave(modelo, numeroSerie);

                } else {

                    if (!aeronave.getModelo().equalsIgnoreCase(modelo)) {

                        System.out.println(
                            "\nNúmero de série já cadastrado com outro modelo."
                        );

                        voltarMenu(in);
                        continue;
                    }
                }

                // Verificar se a aeronave já está vinculada ao piloto
                boolean vinculada = false;

                for (int i = 0; i < pilotoEncontrado.getQtdAeronaves(); i++) {

                    if (pilotoEncontrado.getAeronaves()[i] == aeronave) {

                        vinculada = true;
                        break;
                    }
                }

                if (vinculada) {

                    System.out.println(
                        "\nAeronave já vinculada a este piloto."
                    );

                    voltarMenu(in);
                    continue;
                }

                // Associação entre piloto e aeronave
                pilotoEncontrado.adicionarAeronave(aeronave);

                System.out.println("\nAeronave cadastrada com sucesso.");

                System.out.println("\n*** DADOS DA AERONAVE ***");
                System.out.println(aeronave.toString());
                System.out.println(
                    "Piloto responsável: " + pilotoEncontrado.getNome()
                );

                voltarMenu(in);

            } else if (opcao != 0) {

                System.out.println("\nOpção inválida!");
            }

        } while (opcao != 0);

        System.out.println("Fim do programa!");

        in.close();
    }

    // Método para localizar um piloto pelo CPF
    private static Piloto localizarPiloto(
            Pessoa[] pilotos, int quantidade, String cpf) {

        for (int i = 0; i < quantidade; i++) {

            if (pilotos[i].getCpf().equals(cpf)) {

                return (Piloto) pilotos[i];
            }
        }

        return null;
    }

    // Método para localizar uma aeronave pelo número de série
    private static Aeronave localizarAeronave(
            Pessoa[] pilotos, int quantidade, String numeroSerie) {

        for (int i = 0; i < quantidade; i++) {

            Piloto piloto = (Piloto) pilotos[i];

            for (int j = 0; j < piloto.getQtdAeronaves(); j++) {

                Aeronave aeronave = piloto.getAeronaves()[j];

                if (aeronave.getNumeroSerie().equals(numeroSerie)) {

                    return aeronave;
                }
            }
        }

        return null;
    }

    // Método original disponibilizado pela plataforma
    private static void voltarMenu(Scanner in)
            throws InterruptedException, IOException {

        System.out.println("\nPressione ENTER para voltar ao menu.");

        in.nextLine();

        if (System.getProperty("os.name").contains("Windows"))

            new ProcessBuilder("cmd", "/c", "cls")
                .inheritIO().start().waitFor();

        else

            System.out.print("\033[H\033[2J");

        System.out.flush();
    }
}