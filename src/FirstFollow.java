import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FirstFollow {

    static int np=0;
    static int rnp=0;
    int dc =0;
    static String[][] all = new String[100][2];
    static String[][] resall = new String[100][2];
    private static HashMap<String, Integer> map = new HashMap<String, Integer>();
    public static  String chk="";
    public  static int count=0;

    public static Set<String>[] firstArray = new HashSet[100];
    public static Set<String>[] followArray = new HashSet[100];

    public static void printall()
    {
        for (int i = 0; i <np ; i++) {
            System.out.println(all[i][0]+" -> "+all[i][1]);
        }
    }

    public static Set<String> findFirstFromPro(String a)
    {
        Set<String> set = new HashSet<>();

        if(a.length()==1&&a.matches("[A-Z]"))
        {
            //System.out.println("This is true");
            for (int i = 0; i <np ; i++) {
                if(a.equals(all[i][0]))
                {
                    set.addAll(findFirstFromPro(all[i][1]));
                }
            }
        }

        else if(!a.substring(0,1).matches("[A-Z]"))
        {
            set.add(a.substring(0,1));
            return set;
        }
        else
        {
            set.addAll(findFirstFromPro(a.substring(0,1)));
            if(firstArray[map.get(a.substring(0,1))].contains("$"))
            {
                set.addAll(findFirstFromPro(a.substring(1)));
            }
        }

        return set;
    }

    public static void generateFirst()
    {
        for (int i = 0; i < 100; i++) {
            firstArray[i] = new HashSet<String>();
        }
        //printall();


        for (int i = 0; i <np ; i++) {
            if(!chk.contains(all[i][0]))
            {
                //System.out.println("Inside if");
                map.put(all[i][0],count);
                count++;
                chk+=all[i][0];
            }
        }
        //System.out.println(map);
        //System.out.println(chk+" "+count);
        for (int i = 0; i <np ; i++) {
            if(all[i][1].equals("$"))
            {
                //System.out.println("Inside $");
                //System.out.println(map.get(all[i][0]));
                firstArray[map.get(all[i][0])].add("$");
            }
        }

        chk="";
        for (int i = 0; i < np; i++) {
            if(!chk.contains(all[i][0]))
            {
                chk+=all[i][0];
                firstArray[map.get(all[i][0])].addAll(findFirstFromPro(all[i][0]));
            }

        }

        for (int i = 0; i <count ; i++) {
            System.out.println(firstArray[i]);
        }
    }

    public static Set<String> returnFirst(String a)
    {
        Set<String> set= new HashSet<>();

        if(a.equals(""))
        {
            return set;
        }

        if(a.length()==1)
        {
            if(a.matches("[A-Z]"))
            {
                return firstArray[map.get(a)];
            }
            else
            {
                set.add(a);
                return set;
            }

        }

        if(a.substring(0,1).matches("[A-Z]"))
        {
            set.addAll(firstArray[map.get(a.substring(0,1))]);
            if(set.contains("$"))
            {
                set.addAll(returnFirst(a.substring(1)));
            }
        }
        else
            set.add(a.substring(0,1));

        return set;
    }

    public static void followRuleOne(String a)
    {
        if(a.equals(""))
            return;
        if(a.substring(0,1).matches("[A-Z]"))
        {
            followArray[map.get(a.substring(0,1))].addAll(returnFirst(a.substring(1)));

            if(followArray[map.get(a.substring(0,1))].contains("$"))
            {
                followArray[map.get(a.substring(0,1))].remove("$");
            }
            followRuleOne(a.substring(1));
        }
    }

    public static void followRuleTwo(String a, String b)
    {
        if(a.equals(""))
        {
            return;
        }
        if(a.substring(a.length()-1).matches("[A-Z]"))
        {
            followArray[map.get(a.substring(a.length()-1))].addAll(followArray[map.get(b)]);
            if(firstArray[map.get(a.substring(a.length()-1))].contains("$"))
            {
                followRuleTwo(a.substring(0,a.length()-1),b);
            }
        }
    }

    public static void generateFollow()
    {
        for (int i = 0; i < 100; i++) {
            followArray[i] = new HashSet<String>();
        }

        followArray[map.get(all[0][0])].add("%");

        for (int i = 0; i <np ; i++) {
            if(all[i][1].equals("$"))
                continue;
            else
            {
                followRuleOne(all[i][1]);
            }
        }

        for (int i = 0; i <np ; i++) {
            if(all[i][1].equals("$"))
                continue;
            else
            {
                followRuleTwo(all[i][1],all[i][0]);
            }
        }

        for (int i = 0; i <count ; i++) {
            System.out.println(followArray[i]);
        }


    }

    public static void generateFirstFollow() throws IOException {

        FileInputStream in = new FileInputStream("intermediate.txt");
        String str = "";
        int s;
        while ((s = in.read()) != -1)
        {
            str+=(char)s;
        }
        System.out.println(str);
        String arr[]=str.split("\\n");
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=arr[i].replaceAll("\\s+","");
            String two[]=arr[i].split("=>");
            String res[]=two[1].split("\\|");
            for(int k=0;k<res.length;k++)
            {
                all[np][0]=two[0];
                all[np][1]=res[k];
                np++;
            }
        }
        System.out.println("All First Set");
        generateFirst();
        System.out.println("All Follow Set");
        generateFollow();
    }



}
