import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Matrice {
    public ArrayList<String> userList=  new ArrayList<>();
    public ArrayList<String> themeList=  new ArrayList<>();
    public ArrayList<String> userthemeList=  new ArrayList<>();
    public int [][] matriceMUT ;
    public String matriceName="log-reco.txt";
    public Matrice(String initfilename) {
        this.readFile(initfilename);
        this.matriceMUT = new int[userList.size()][themeList.size()];
        this.cal_MatriceMUT();
        this.toFile();
        this.printMatriceMUT();
    }
    public void initMatriceMUT(){
        this.matriceMUT = new int[userList.size()][themeList.size()];
        for (int i = 0; i < userList.size(); i++) {
            for (int j = 0; j < themeList.size(); j++) {
                this.matriceMUT[i][j]= 0;
            }
        }
    }
    public void cal_MatriceMUT(){
        String tmpStr;
        this.initMatriceMUT();
        for (int i = 0; i < userList.size(); i++) {
            for (int j = 0; j < themeList.size(); j++) {
                tmpStr= userList.get(i)+themeList.get(j);
                for (String line : userthemeList) {
                    if(tmpStr.equals( line) )  matriceMUT[i][j]+= 1;
                }


            }
        }

    }
    public void toFile(){
        File themeFile   = new File ("theme.txt");
        File userFile    =   new File ("user.txt");
        File matriceFile = new File ("matriceMUT.txt");

        try
        {
            PrintWriter tpw = new PrintWriter (new BufferedWriter (new FileWriter (themeFile)));
            for (String theme : themeList) {
                tpw.println(theme);
            }
            tpw.close();

            PrintWriter upw = new PrintWriter (new BufferedWriter (new FileWriter (userFile)));
            for (String user : userList) {
                upw.println(user);
            }
            upw.close();

            PrintWriter mutpw = new PrintWriter (new BufferedWriter (new FileWriter (matriceFile)));
            for (int i = 0; i < userList.size(); i++) {
                for (int j = 0; j < themeList.size(); j++) {
                    mutpw.print(this.matriceMUT[i][j]+";");
                }
                mutpw.print( "\n");
            }
            mutpw.close();
        }
        catch (IOException exception)
        {
            System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
        }
    }

    public void printMatriceMUT(){
        for (String theme : themeList) System.out.print(theme+" ");
        System.out.println();
        for (int i = 0; i < userList.size(); i++) {
            System.out.print(userList.get(i)+" ");
            for (int j = 0; j < themeList.size(); j++) {
                System.out.print(  "   "+matriceMUT[i][j]+"  " );
            }
            System.out.println("");
        }

    }
    public void readFile(String filename){
        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader (fr);
            String line = br.readLine();
            String user,theme;
            while (line != null)
            {    user = line.split(";")[1];
                theme = line.split(";")[2];
                userthemeList.add(user+theme);
                if(!userList.contains(user) ) userList.add(user);
                if(!themeList.contains(theme) )  themeList.add(theme);
                line = br.readLine();
            }
            br.close();
            fr.close();
        }
        catch (IOException exception){ System.out.println ("Errors : " + exception.getMessage());}




    }

}
