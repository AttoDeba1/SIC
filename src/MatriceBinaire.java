import java.io.*;
import java.util.ArrayList;

public class MatriceBinaire extends Matrice {
    /**
     * transformation binaire de MUT
     */
    public  int [][] MutBinaire;
    /**
     * matrice de distance entre les themes
     */
    public  int [][] Mtt;
    /**
     * transformation binaire de @Mtt
     */
    public  int [][] MttBinaire;
    public int nbCustomer;
    public int nbTheme;
    public  int [][] matriceMUTA;


    public MatriceBinaire(String initfilename) {
        super(initfilename);
        this.readMUTFile("matriceMUT.txt");
        this.MutBinaire= this.toBinaire(this.matriceMUTA);
        this.toFile("matriceMUTBinaire.txt");
    }
    public int [][] toBinaire(final int[][] matrice){
        int[][] matriceBinaire= new int[userList.size()][themeList.size()];
        for (int i = 0; i <userList.size() ; i++) {
            for (int j = 0; j <themeList.size() ; j++) {
                matriceBinaire[i][j]= (0!=matrice[i][j])?
                                       1
                                      :0;
            }
        }

       return  matriceBinaire;
    }
    public void readMUTFile(String filename){
        try
        {   ArrayList Mut = new ArrayList<int[]>();
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader (fr);
            String line = br.readLine();
            while (line != null)
            {
                Mut.add(line.split(";"));
                System.out.println(line);
                line = br.readLine();
            }
            br.close();
            fr.close();
            String[] Line;
            this.nbCustomer= Mut.size();
            this.nbTheme= ((String[])Mut.get(0) ).length;
            this.matriceMUTA= new int[nbCustomer][nbTheme];
            for (int i = 0; i <nbCustomer ; i++) {
                Line = (String[])Mut.get(i);
                for (int j = 0; j < Line.length ; j++) {
                    matriceMUTA[i][j]= new Integer( Line[j] );
                    System.out.print( Line[j]+" ");

                }
                System.out.println("");

            }
        }
        catch (IOException exception){ System.out.println ("Errors : " + exception.getMessage());}

    }

    public void toFile( String filenameMUTB){
       /// File themeFile   = new File ("theme.txt");
      //  File userFile    =   new File ("user.txt");
        File matriceMUTBFile = new File (filenameMUTB);

        try
        {
            PrintWriter tpw = new PrintWriter (new BufferedWriter (new FileWriter (matriceMUTBFile)));
            for (int i = 0; i <nbCustomer ; i++) {
                for (int j = 0; j <nbTheme; j++) {
                    tpw.print(MutBinaire[i][j]+ ";");
                }
                tpw.println("");
            }
            tpw.close();



        }
        catch (IOException exception)
        {
            System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
        }
    }

}