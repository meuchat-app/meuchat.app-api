package app.meuchat.server.service;

import app.meuchat.server.model.Message;
import app.meuchat.server.model.MeuChat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskManegerService extends MeuChat {

    List<String> tasks = new ArrayList<String>();
    String context = "0";
    String response = "";

    String mensagemDeBoasVindas = "Olá, eu sou o TaskManeger, em que posso ajudar? \n"
        + "1 - Adicionar tarefa \n"
        + "2 - Listar tarefas \n"
        + "3 - Remover tarefa";
    String mensagemDeAdicionarTarefa = "Qual tarefa deseja adicionar? Digite 0 para cancelar";
    String mensagemDeRemoverTarefa = "Qual tarefa deseja remover? Digite 0 para cancelar";
    String mensagemDeNaoHaTarefasCadastradas = "Não há tarefas cadastradas. Digite 0 se quiser ver as opções novamente.";
    String mensagemDeOpcaoCancelada = "Operação cancelada. Digite 0 se quiser ver as opções novamente.";
    String mensagemDeOpcaoInvalida = "Opção inválida! \n Digite 0 se quiser ver as opções novamente.";
    String mensagemDeTarefaAdicionadaComSucesso = "Tarefa adicionada com sucesso! \n Digite 0 se quiser ver as opções novamente.";
    String mensagemDeTarefaRemovidaComSucesso = "Tarefa removida com sucesso! \n Digite 0 se quiser ver as opções novamente.";
    String mensagemDeTarefaNaoEncontrada = "Tarefa não encontrada! \n Digite 0 se quiser ver as opções novamente.";







    @Override
    public void receiveMessage(Message msg) {
        System.out.println(msg.getMessage());

        if (!response.equals("") && context.equals("0")) {
            if (msg.getMessage().matches("[0-3]*")) {
                context = msg.getMessage();
            } else {
                response = mensagemDeOpcaoInvalida;
                chat.setResponse(response);
                return;
            }
        }

        switch (context) {
            case "0":
                wellCome(msg);
                break;

            case "1":
                addTask(msg);
                break;
            
            case "2":
                listTask();
                break;
            
            case "3":
                removeTask(msg);
                break;
        }

    }
    
    public void wellCome(Message msg) {    
        response = mensagemDeBoasVindas;
        chat.setResponse(response);
        
    }
    
    public void addTask(Message msg) {
        if (!response.equals(mensagemDeAdicionarTarefa)) {
            response = mensagemDeAdicionarTarefa;
            chat.setResponse(response);
        } else {
            if (!msg.getMessage().equals("0")) {
                tasks.add(msg.getMessage());
                response = mensagemDeTarefaAdicionadaComSucesso;
                chat.setResponse(response);
                context = "0";
            } else {
                response = mensagemDeOpcaoCancelada;
                chat.setResponse(response);
                context = "0";
            }
        }
        };

    
    public void listTask() {
        if (tasks.size() > 0) {
            String list = "";
            for (int i = 0; i < tasks.size(); i++) {
                list += (i+1) + " - " + tasks.get(i) + "\n";
            }
            response = list;
            chat.setResponse(response);
            context = "0";
        } else {
            response = mensagemDeNaoHaTarefasCadastradas;
            chat.setResponse(response);
            context = "0";
        }
    };
    
    public void removeTask(Message msg){
        if (tasks.size() > 0) {
            
                String list = "";
                for (int i = 0; i < tasks.size(); i++) {
                    list += (i+1) + " - " + tasks.get(i) + "\n";
                }

                if (!response.contains(mensagemDeRemoverTarefa)) {
                    response = list + mensagemDeRemoverTarefa;
                    chat.setResponse(response);

                } else {
                    if (!msg.getMessage().equals("0")) {
                        if(msg.getMessage().matches("[0-"+(tasks.size())+"]")){
                            tasks.remove(Integer.parseInt(msg.getMessage()) - 1);
                            response = mensagemDeTarefaRemovidaComSucesso;
                            chat.setResponse(response);
                            context = "0";
                        }else{
                            response = mensagemDeOpcaoInvalida;
                            chat.setResponse(response);
                            context = "0";
                        }
                    } else {
                        response = mensagemDeOpcaoCancelada;
                        chat.setResponse(response);
                        context = "0";
                    }
                }
            
        } else {
            response = mensagemDeNaoHaTarefasCadastradas;
            chat.setResponse(response);
            context = "0";
        }
    };
}
