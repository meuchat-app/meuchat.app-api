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

    @Override
    public void receiveMessage(Message msg) {
        System.out.println(msg.getMessage());

        if (!response.equals("") && context.equals("0")){
            if (msg.getMessage().equals("1") || (msg.getMessage().equals("2") || msg.getMessage().equals("3"))) {
                context = msg.getMessage();
            } else {
                response = "Opção inválida";
                chat.setResponse(response);
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
                response = "Tarefa adicionada com sucesso!  Digite 0 se quiser ver as opções novamente.";
                chat.setResponse(response);
                context = "0";
            } else {
                response = "Operação cancelada. Digite 0 se quiser ver as opções novamente.";
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
            response = "Não há tarefas cadastradas.  Digite 0 se quiser ver as opções novamente.";
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

            if (!response.equals(list + mensagemDeRemoverTarefa)) {
                response = list + mensagemDeRemoverTarefa;
                chat.setResponse(response);

            } else {
                if (!msg.getMessage().equals("0")) {
                    tasks.remove(Integer.parseInt(msg.getMessage())-1);
                    response = "Tarefa removida com sucesso! Digite 0 se quiser ver as opções novamente.";
                    chat.setResponse(response);
                    context = "0";
                } else {
                    response = "Operação cancelada";
                    chat.setResponse(response);
                    context = "0";
                }
            }
        } else {
            response = "Não há tarefas cadastradas";
            chat.setResponse(response);
            context = "0";
        }
    };
}
