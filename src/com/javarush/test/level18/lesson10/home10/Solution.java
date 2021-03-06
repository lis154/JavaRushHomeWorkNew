package com.javarush.test.level18.lesson10.home10;

/* Собираем файл
Собираем файл из кусочков
Считывать с консоли имена файлов
Каждый файл имеет имя: [someName].partN. Например, Lion.avi.part1, Lion.avi.part2, ..., Lion.avi.part37.
Имена файлов подаются в произвольном порядке. Ввод заканчивается словом "end"
В папке, где находятся все прочтенные файлы, создать файл без приставки [.partN]. Например, Lion.avi
В него переписать все байты из файлов-частей используя буфер.
Файлы переписывать в строгой последовательности, сначала первую часть, потом вторую, ..., в конце - последнюю.
Закрыть потоки. Не использовать try-with-resources
*/

import java.io.*;
import java.util.*;

import static com.javarush.test.level18.lesson10.home10.Solution.FailR.snon;

public class Solution {
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> spisok = new ArrayList<>();


        String name;
        while(true)
        {
            name = reader.readLine();
            if (name.equals("end"))
            {
                break;
            }
            else
            {
                spisok.add(name);

            }
        }
        reader.close();



        ArrayList<FailR> spisokF = new ArrayList<>();
        for (int j = 0; j < spisok.size(); j++) // проходим начальный файл
        {

            String[] toch = spisok.get(j).split("\\.");
            String  name1="";
            int nomer;
            for (int i = 0; i < toch.length-1; i++)
            {
                if (i != toch.length-2)
                {
                    name1 = name1 + toch[i] + ".";
                }
                else
                {
                    name1 = name1 + toch[i];
                }
            }
            nomer = Integer.parseInt(toch[toch.length - 1].replace("part",""));
            FailR fairR = new FailR();
            fairR.setName(name1);
            fairR.setNumber(nomer);
            spisokF.add(fairR);

            Collections.sort(spisokF, snon);
        }
        ArrayList<String> spisokFailOts = new ArrayList<>();
        for (FailR failT: spisokF)
        {
            spisokFailOts.add(failT.getName() + ".part" + failT.getNumber());
        }

        for (String k: spisokFailOts)
        {
            System.out.println(k);
        }


        FileOutputStream failZ = new FileOutputStream(spisokF.get(0).getName()+".part", true);
        for(String nameFa: spisokFailOts)
        {
            FileInputStream failCht = new FileInputStream(nameFa);
            byte[] buffer = new byte[failCht.available()];
                    while(failCht.available()>0)
                    {
                        failCht.read(buffer);
                        failZ.write(buffer);
                    }
            failCht.close();
        }
        failZ.close();

    }
    
    public static class FailR
    {
        String name;

        int number;

        public FailR(String name, String part, int number)
        {
            this.name = name;
            this.number = number;
        }

        public FailR()
        {
        }

        public String getName()
        {
            return name;
        }

        public int getNumber()
        {
            return number;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void setNumber(int number)
        {
            this.number = number;
        }

        static Comparator<FailR> snon = new Comparator<FailR>()
        {
            @Override
            public int compare(FailR o1, FailR o2)
            {
                return o1.number - o2.number;
            }
        };

    }
}
