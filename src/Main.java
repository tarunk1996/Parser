import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    static int np=0;
    static int rnp=0;
    static String[][] all = new String[100][2];
    static String[][] resall = new String[100][2];

    public  static void removeLeftRecursion(String a)
    {
        for(int i=0;i<np;i++)
        {

            if(a.equals(all[i][0]))
            {
                if(all[i][0].equals(all[i][1].substring(0,1)))
                {
                    resall[rnp][0]=all[i][0]+"'";
                    resall[rnp][1]=all[i][1].substring(1)+all[i][0]+"'"+"| \u03B5";
                    rnp++;
                }
                else
                {
                    resall[rnp][0]=all[i][0];
                    resall[rnp][1]=all[i][1]+all[i][0]+"'";
                    rnp++;
                }
                //System.out.println("Left Recursion detected in "+ all[i][0]);
            }

            //System.out.println(all[i][0]+"->"+all[i][1]);
        }
    }

    public static void main(String[] args) {
        //System.out.println("Hello, World!");

        String accessed = "";

        int errno=0;
        try {
            //FileReader in = new FileReader("input.txt");
            FileInputStream in = new FileInputStream("input.txt");
            //System.out.println("File Read");

            //BufferedReader br = new BufferedReader(in);
            int s;
            char b;
            int flag=0;

            String str="";
            String chrstr="";
            int lno=1,te=0;

            int a=0;
            while ((s = in.read()) != -1)
            {
                b=(char)s;
                str+=b;
            }
            String arr[]=str.split("\\n");
            for(int i=0;i<arr.length;i++)
            {
                arr[i]=arr[i].replaceAll("\\s+","");
                String two[]=arr[i].split("=>");
                if(two.length!=2)
                {
                    System.err.print("Error in Production Statement");
                    break;
                }
                else
                {
                    //System.out.println(two[0]);
                    if(two[0].equals("[A-Z]"))
                    {
                        System.err.print("Grammar is not a CFG");
                        break;
                    }
                    else
                    {
                        String res[]=two[1].split("\\|");
                        for(int k=0;k<res.length;k++)
                        {
                            all[np][0]=two[0];
                            all[np][1]=res[k];
                            np++;
                        }

                    }
                }

                //System.out.println(arr[i]);
            }
            for(int i=0;i<np;i++)
            {
                //System.out.println("sfd "+all[i][1].substring(0,1));
                if((all[i][1].contains(all[i][0]))&&(all[i][0].equals(all[i][1].substring(0,1)))&&!accessed.contains(all[i][0]))
                {
                    System.out.println("Left Recursion detected in "+ all[i][0]);
                    accessed+=all[i][0];
                    removeLeftRecursion(all[i][0]);
                }
                else
                {
                    resall[rnp][0]=all[i][0];
                    resall[rnp][1]=all[i][1];
                    rnp++;
                }

                System.out.println(all[i][0]+"->"+all[i][1]);
            }
            System.out.println("New Productions are:");
            for(int j=0;j<rnp;j++)
            {
                System.out.println(resall[j][0]+"->"+resall[j][1]);
            }


        }
        catch (FileNotFoundException ex)
        {
            System.err.println("File Not Found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
