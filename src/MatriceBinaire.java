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
    public  double [][] Mtt;
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
        this.Mtt = new double[this.nbCustomer][this.nbCustomer];
        this.MttBinaire = new int[this.nbCustomer][this.nbCustomer];
        this.toMtt();
        this.mttToBinaire(0.5);
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
    private  int getIntersection(int customer1, int customer2){
        int intersection=0;
        for (int j = 0; j < this.nbTheme ; j++) {
            if( (this.MutBinaire[customer1][j]==1)  && (this.MutBinaire[customer2][j]==1) )
                          intersection++;
        }
        System.out.println ("intersection == "+intersection);
        return  intersection;
    }
    private   int getUnion(int customer1, int customer2){
        int union=0;
        for (int j = 0; j < this.nbTheme ; j++) {
            if(this.MutBinaire[customer1][j]==1  || this.MutBinaire[customer2][j]==1)
            union++;
        }
        System.out.println ("union == "+union);
        return  union;
    }
    private double getDistance(int customer1, int customer2){
          double intersection=getIntersection(customer1,customer2);
                  double union=getUnion(customer1, customer2);
                  System.out.println("distance"+( 1-(2/3.1) ));
        return  (1- (intersection / union) );
    }
    private void initMtt(){
        for (int i = 0; i <nbCustomer ; i++) {
            for (int j = 0; j <nbCustomer ; j++) {
                this.Mtt[i][j]=0.0;
            }
        }
    }
    public  void  toMtt(){
        this.initMtt();
        for (int i = 0; i < nbCustomer ; i++) {
            for (int j = i; j < nbCustomer; j++) {
                this.Mtt[i][j] = getDistance(i, j);
            }
        }
    }
    public  void mttToBinaire( double marge){
        for (int i = 0; i <nbCustomer ; i++) {
            for (int j = i; j <nbCustomer ; j++) {
                this.MttBinaire[i][j] =  (this.Mtt[i][j]<=marge)? 1:0;
            }
        }

    }
    public void toFile( String filenameMUTB){
        File mttBinaireFile   = new File ("matriceMttBinaire.txt");
        File mttFile    =   new File ("matriceMtt.txt");
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

            tpw = new PrintWriter (new BufferedWriter (new FileWriter (mttFile)));
            for (int i = 0; i <nbCustomer ; i++) {
                for (int j = 0; j <nbCustomer; j++) {
                    tpw.print(Mtt[i][j]+ ";");
                }
                tpw.println("");
            }
            tpw.close();
            tpw = new PrintWriter (new BufferedWriter (new FileWriter (mttBinaireFile)));
            for (int i = 0; i <nbCustomer ; i++) {
                for (int j = 0; j <nbCustomer; j++) {
                    tpw.print(MttBinaire[i][j]+ ";");
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