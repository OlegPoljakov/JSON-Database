package server;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import server.cli.CommandExecutor;
import server.cli.commands.DeleteCommand;
import server.cli.commands.GetCommand;
import server.cli.commands.SetCommand;
import server.cli.requests.Request;
import server.cli.requests.Response;
import server.exceptions.NoSuchRequestException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
        System.out.println("Server started!");
        JSONArray datalist = new JSONArray();

        while (!server.isClosed())
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = server.accept();

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos, datalist, server);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                server.close();
            }
        }
    }
}

// ClientHandler class
class ClientHandler extends Thread
{

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private JSONArray datalist;
    private ServerSocket server;
    private boolean flag = false;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, JSONArray datalist, ServerSocket server)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.datalist = datalist;
        this.server = server;
    }

    @Override
    public void run() {

        final CommandExecutor executor = new CommandExecutor();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();

        while (!flag)
        {
            try {
                Request request = new Gson().fromJson(dis.readUTF(), Request.class);
                Response response = new Response();

                try {
                    switch (request.getType()) {
                        case "get":
                            readLock.lock();
                            GetCommand getCmd = new GetCommand(request.getKey());
                            executor.executeCommand(getCmd);
                            response.setValue(getCmd.getResult());
                            readLock.unlock();
                            break;
                        case "set":
                            writeLock.lock();
                            SetCommand setCmd = new SetCommand(request.getKey(), request.getValue(), datalist);
                            executor.executeCommand(setCmd);
                            writeLock.unlock();
                            break;
                        case "delete":
                            writeLock.lock();
                            DeleteCommand deleteCmd = new DeleteCommand(request.getKey());
                            executor.executeCommand(deleteCmd);
                            writeLock.unlock();
                            break;
                        case "exit":
                            response.setResponse(Response.STATUS_OK);
                            dos.writeUTF(response.toJSON());
                            server.close();
                            return;
                        default:
                            throw new NoSuchRequestException();
                    }
                    response.setResponse(Response.STATUS_OK);
                }
                catch (Exception e) {
                    response.setResponse(Response.STATUS_ERROR);
                    response.setReason(e.getMessage());
                }
                finally {
                    dos.writeUTF(response.toJSON());
                    flag = true;
                }

            }
            catch (Exception e) {
                try {
                    dis.close();
                    dos.close();
                    s.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                e.printStackTrace();
            }
        }
    }
}
