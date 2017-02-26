import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    static int np=0;
    static int rnp=0;
    int dc =0; //dash count
    static String[][] all = new String[100][2];
    static String[][] resall = new String[100][2];

    public static String greatestCommonPrefix(String a, String b) {
        int minLength = Math.min(a.length(), b.length());
        //System.out.println(a + "  "+b);
        for (int i = 0; i < minLength; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.substring(0, i);
            }
        }
        return a.substring(0, minLength);
    }

    public static void findFactor(int s,int e)
    {
        System.out.println(s+" "+e);
        String cstr [] = new String[100];
        int countstr=0;
        for (int i = s; i < e-1; i++)
        {
            String a=all[i][1];
            String z;
            for (int j = i+1; j < e; j++)
            {

                z=greatestCommonPrefix(a,all[j][1]);
                //System.out.println(j);
                //System.out.println(z+" "+all[j][1]);
                if(!z.equals(""))
                {

                    i=j;
                    a=z;
                    //System.out.println(a+"  "+i);

                }
                //System.out.println(a+"  : "+j);
            }
            //System.out.println(a);
            cstr[countstr++]=a;

            /*for (int j = s; j < e; j++)
            {
                if(all[j][1].substring(0,1).contains(a.substring(0,1)))
                {
                    resall[rnp][0]="Z";
                    resall[rnp][1]=
                }
            }*/

        }
        for (int i = 0; i <countstr ; i++)
        {
            System.out.println(cstr[i]);
        }

    }

    public static void removeLeftFactoring()
    {
        int pre=0,next=0;
        String s=all[0][0];
        //System.out.println(s);
        for(int i=1;i<np;i++)
        {
            if(!all[i][0].equals(s))
            {
                next=i;
                findFactor(pre,next);

                pre=i;
                s=all[i][0];
                //System.out.println(s);
            }
        }
        findFactor(pre,np );
    }

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

    public static void main(String[] args) throws IOException {
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
                    //String l = "A";
                    //System.out.println(two[0]);
                    if(!two[0].matches("[A-Z]"))
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
            removeLeftFactoring();
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

        FirstFollow ff = new FirstFollow();
        ff.generateFirstFollow();

    }

}
