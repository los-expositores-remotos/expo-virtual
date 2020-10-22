package ar.edu.unq;


import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ConexionMongoJava {

    public static void main(String[] args) {

        try{

        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://Gustavo:99z2CEj2xWnR4Ntw@cluster0.agdwn.mongodb.net/feriavirtualdb?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("test");

        }catch (Error e){
            System.out.print(e);
        }


    }

}
