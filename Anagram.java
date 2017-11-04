import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;


public class Anagram
{	
	MyHashMap hm; 
	
	Vector<String> vec=new Vector<String>();
	//int size=0;//it is the of array not equal to number of words
	int no_of_collision=0;//for debugging
	int no_of_String=0;
	public Anagram(int no_of_String1)
	{
		int nextPrime=0;
		//size=74177;//////////WARNING
		//finding the next prime number just after no_of_String
		nextPrime=no_of_String1;//Change this later get prime nummber NDJNJDSNJKNNNNNNNNNNNNNNNSKjNDNSDKJNKSNSNDJSNJN;kdn
		hm=new MyHashMap(nextPrime);
		no_of_String=no_of_String1;
	}
	
	public Vector<String> findanagrams(String input_str)//It finds the anagram of word input_str
	{	
		Vector<String> Result=new Vector<String>();
		Vector<String> Resultkey=new Vector<String>();
		MyHashMap av_copy=new MyHashMap(6719);
		char[] ch=input_str.toCharArray(); 
		//char[] tem="cooking".toCharArray();
		//System.out.println(InsertionSort(tem));
		String Sorted_str=InsertionSort(ch);//Input String is Sorted
		if(hm.HaveKey(Sorted_str))
			Result.addAll(hm.get(Sorted_str));
		/*for(int i=0;i<Result.size();i++)//checking
		{
			System.out.println(Result.get(i));
		}*/
		//here I will write a function
		//Vector<String> AllsubString=SubStrings(Sorted_str);
		Vector<String> AllsubString=new Vector<String>();
		RecurrsiveSubstrings(Sorted_str,AllsubString,0,Sorted_str.length());
		//checking
		/*System.out.println(AllsubString.size());
		for(int i=0;i<AllsubString.size();i++)
		{
			System.out.println(AllsubString.get(i));
		}*/
		//checking
		Vector<Vector<String>> str_sizes=new Vector<Vector<String>>();//contains String according to their length
		//0 space anagram
		Vector<Vector<String>> helper=new Vector<Vector<String>>();
		for(int i=0;i<=input_str.length();i++)
		{
			Vector<String> vec=new Vector<String>();
			str_sizes.add(i, vec);
			helper.add(new Vector<String>());
		}
		//System.out.println("0 space complete");
		if(input_str.length()<6)
			return Result;
		for(int i=0;i<=input_str.length();i++)//only the Required elements get added
		{	
			Vector<String> vec=new Vector<String>();
			for(int j=0;j<AllsubString.size();j++)
			{
				if(AllsubString.get(j).length()==i)
				{
					vec.add(AllsubString.get(j));
				}
			}
			str_sizes.add(i, vec);			
		}
		/*for(int i=0;i<str_sizes.size();i++)
		{
			//System.out.print(i+" "+str_sizes.get(i).size());
			System.out.print(i+" :");
			for(int j=0;j<str_sizes.get(i).size();j++)
			{
				System.out.print(str_sizes.get(i).get(j)+",");
			}
			System.out.println("");
		}*/
		
		if(input_str.length()<9)
		{	
			for(int i=1;i<=input_str.length();i++)
			{
				for(int m=0;m<str_sizes.get(i).size();m++)
				{
					if(hm.HaveKey(str_sizes.get(i).get(m)))
					{
						//System.out.println("i :"+ i+" " +helper.get(i));
						helper.get(i).add(str_sizes.get(i).get(m));
					}
				}
			}
		
			str_sizes=helper;
		}
		//One space anagram(This will work for any of words)
		int k=3;
		int j=input_str.length()-k;
		for(int i=3;i<=input_str.length()-3;i++)//finding Anagrams
		{	//System.out.println(" i: "+i);
			try
			{
				for(j=input_str.length()-k;j>=i;j--)
				{   //System.out.println(" j: "+j);
					for(int travi=0;travi<str_sizes.get(i).size();travi++)
						{	//System.out.println("travi " +"i "+i+" "+ str_sizes.get(i).size());
							if(hm.HaveKey(str_sizes.get(i).get(travi)))
							{	
								for(int travj=0;travj<str_sizes.get(j).size();travj++)
								{
									if(hm.HaveKey(str_sizes.get(j).get(travj)))
									{
										char chh[]=(str_sizes.get(i).get(travi)+str_sizes.get(j).get(travj)).toCharArray();
										String s0=InsertionSort(chh);
										if(s0.equals(Sorted_str))
										{
											//this if is for avoiding multiple copies
											if(!(av_copy.HaveKey(str_sizes.get(i).get(travi)) && av_copy.HaveKey(str_sizes.get(j).get(travj))) )
											//value should be added not key
											{
												Vector<String> one=hm.get(str_sizes.get(i).get(travi));
												Vector<String> two=hm.get(str_sizes.get(j).get(travj));
												for(int m=0;m<one.size();m++)
												{
													for(int n=0;n<two.size();n++)
													{
														Result.add(one.get(m)+" "+two.get(n));
														Result.add(two.get(n)+" "+one.get(m));
													}
												}
												av_copy.put(str_sizes.get(i).get(travi), "avoiding_copies");
												av_copy.put(str_sizes.get(j).get(travj), "avoiding copies");
												Resultkey.add(str_sizes.get(i).get(travi)+" "+str_sizes.get(j).get(travj));
												Resultkey.add(str_sizes.get(j).get(travj)+" "+str_sizes.get(i).get(travi));
											}
										}
									}
									else
									{
										char chh[]=(str_sizes.get(i).get(travi)+str_sizes.get(j).get(travj)).toCharArray();
										String s0=InsertionSort(chh);
										if(s0.equals(Sorted_str))
										{	
											//this if is for avoiding multiple copies
											if(!(av_copy.HaveKey(str_sizes.get(i).get(travi)) && av_copy.HaveKey(str_sizes.get(j).get(travj))) )
											//value should be added not key
											{
											Resultkey.add(str_sizes.get(i).get(travi)+" "+str_sizes.get(j).get(travj));
											Resultkey.add(str_sizes.get(j).get(travj)+" "+str_sizes.get(i).get(travi));
											}	
										}
									}
								}
						
							}
							else 
							{
								for(int travj=0;travj<str_sizes.get(j).size();travj++)
								{
									//if(hm.HaveKey(str_sizes.get(j).get(travj)))
									//{
										char chh[]=(str_sizes.get(i).get(travi)+str_sizes.get(j).get(travj)).toCharArray();
										String s0=InsertionSort(chh);
										if(s0.equals(Sorted_str))
										{	
											Resultkey.add(str_sizes.get(i).get(travi)+" "+str_sizes.get(j).get(travj));
											Resultkey.add(str_sizes.get(j).get(travj)+" "+str_sizes.get(i).get(travi));
										}
									//}
								}
							}
						}
				}
			}
			catch (NullPointerException e)
			{
				System.out.println("Null Pointer Exception");
			}
		}
		//System.out.println("Result size"+ Result.size());
		//if(input_str.length()<9)
		//	return Result;
		//two space anagram 
		//helping
		////////////////////////////
		/*for(int i=0;i<str_sizes.size();i++)
		{
			//System.out.print(i+" "+str_sizes.get(i).size());
			for(int g=0;g<str_sizes.get(i).size();g++)
			{
				System.out.print("i: "+i+" "+str_sizes.get(i).get(g)+",");
			}
			System.out.println("");
		}*/
		///////////////////////////
		if(input_str.length()>=9)//Only with this case two spaces are allowed
		{
			//Result=RemoveDuplicates(Result);
			Resultkey=RemoveDuplicates(Resultkey);
			Result.addAll(getallsplit(Resultkey,input_str));
		}
		Result=RemoveDuplicates(Result);
		return Result;
	}
	public Vector<String> getallsplit(Vector<String> Resultkey,String input_str)
	{
		Vector<String> two_spaces=new Vector<String>();
		for(int i=0;i<Resultkey.size();i++)
		{
			String[] spli=Resultkey.get(i).split(" ");
			if(spli[0].length()>=6 && spli.length==2)
			{
				Vector<String> vec;
				if(hm.HaveKey(spli[1]))
				{	
					vec=findanagrams(spli[0]);//from here I get copies
					for(int j=0;j<vec.size();j++)
					{
						if(vec.get(j).length()==1+spli[0].length())//It means they have a spce in between them
						{
							Vector<String> full;
							//System.out.println(spli[0]);
							full=hm.get(spli[1]);
							//System.out.println("hello");
							//System.out.println(getcombinations(full,vec.get(j)).size());
							two_spaces.addAll(getcombinations(full,vec.get(j)));
						}
					}
				}
			}
		}
		return two_spaces;
		
	}
	public Vector<String> getcombinations(Vector<String> full,String split_it)
	{
		Vector<String> two_space=new Vector<String>();
		String[] s=split_it.split(" ");
		/*System.out.println("Full printing");
		for(int i=0;i<full.size();i++)
		{
			System.out.println(full.get(i));
		}
		System.out.println("end");*/
		for(int i=0;i<full.size();i++)
		{
			String str1=full.get(i)+" "+s[0]+" "+s[1];
			String str2=full.get(i)+" "+s[1]+" "+s[0];
			two_space.add(str1);
			two_space.add(str2);
		}
		for(int i=0;i<full.size();i++)
		{
			String str1=s[0]+" "+full.get(i)+" "+s[1];
			String str2=s[0]+" "+s[1]+" "+full.get(i);
			two_space.add(str1);
			two_space.add(str2);
		}
		for(int i=0;i<full.size();i++)
		{
			String str1=s[1]+" "+full.get(i)+" "+s[0];
			String str2=s[1]+" "+s[0]+" "+full.get(i);
			two_space.add(str1);
			two_space.add(str2);
		}
		return two_space;
	}
	public String[] Permute(String TwoSpace)
	{
		String[] per=new String[6];
		String[] s=TwoSpace.split(" ");
		String sd=new String("");
		for(int j=0;j<s.length;j++)
		{
			sd+=s[j]+" ";
		}
		sd.substring(0,sd.length()-1);
		per[0]=sd;
		per[1]=s[0]+" "+s[2]+" "+s[1];
		per[2]=s[1]+" "+s[0]+" "+s[2];
		per[3]=s[1]+" "+s[2]+" "+s[0];
		per[4]=s[2]+" "+s[0]+" "+s[1];
		per[5]=s[2]+" "+s[1]+" "+s[0];
		return per;
	}
	/*public Vector<String> GetThreeSet(int len,Vector<String> rndset,Vector<String> ThreeSet)
	{
		Vector<String> TwoSet=GetTwoSet(rndset);//this non-copy view
		for(int i=0;i<rndset.size()-2;i++)
		{		
			for(int j=0;j<TwoSet.size();j++)
			{
				if(rndset.get(i).length()+TwoSet.get(j).length()-1==len)
				ThreeSet.add(new String(rndset.get(i)+" "+TwoSet.get(j)));
			}
		}
		return ThreeSet;
	}
	public Vector<String> GetTwoSet(Vector<String> subset)
	{
		Vector<String> twoSet=new Vector<String>();
		for(int i=0;i<subset.size()-1;i++)
		{
			for(int j=i+1;j<subset.size();j++)
			{
				twoSet.add(new String(subset.get(i)+" "+subset.get(j)));
			}
		}
		return twoSet;
	}*/
	public Vector<String> SubStrings(String Sorted_str)
	{
		//It is still missing some cases
		Vector<String> Poss_str=new Vector<String>();
		for (int i = 0; i < Sorted_str.length(); i++) 
		{   for (int j = i+1; j <= Sorted_str.length(); j++)
			{
				Poss_str.add(Sorted_str.substring(i, j));
			}
		}       
		return Poss_str;//All the SubStrings
		
	}
	public Vector<String> RecurrsiveSubstrings(String Sorted_str,Vector<String> AllSubstrings,int index,int len)
	{	
		if(len-index==1)//Base Case
		{
			//System.out.println("I'm here");
			//Vector<String> simple=new Vector<String>();
			//simple.add(Sorted_str);
			//System.out.println(Sorted_str);
			AllSubstrings.add(Sorted_str);
			AllSubstrings.add("");
			return AllSubstrings;
			
		}
		index+=1;
		//System.out.println(Sorted_str);
		//System.out.println(index);
		Vector<String> temp=RecurrsiveSubstrings(Sorted_str.substring(1, Sorted_str.length()),AllSubstrings,index,len);
		//AllSubstrings.addAll(temp);
		Vector<String> t=new Vector<String>();
		for(int j=0;j<AllSubstrings.size();j++)
		{
			//System.out.println(AllSubstrings.size());
			//System.out.println(index-1);
			String n=Sorted_str.charAt(0)+AllSubstrings.get(j);
			t.add(n);
		}
		AllSubstrings.addAll(t);
		//System.out.println(AllSubstrings.size());
		return AllSubstrings;
	}
	public Vector<String> SubString(Vector<String> vec_str,int len)//length of substring you want
	{
		Vector<String> v_str=new Vector<String>();
		for(int i=0;i<vec_str.size();i++)
		{
			if(vec_str.get(i).length()==len)
			{
				v_str.add(vec_str.get(i));
			}
		}
		return v_str;
		//return null;
	}
	public void FillArr(String str)
	{
				char[] ch=str.toCharArray();  
				hm.put(InsertionSort(ch), str);
				//hm.put(str, str);
				
	}
	
	public String InsertionSort(char[] array)
	{
		
        int n = array.length;
        for (int j = 1; j < n; j++) {
            char key = array[j];
            int i = j-1;
            while ( (i > -1) && ( array [i] > key ) ) {
                array [i+1] = array [i];
                i--;
            }
            array[i+1] = key;
                       
        }
        String str=new String(array);
        //System.out.println(str);
        return str;
	}
	public void Checker()
	{
		for(int i=0;i<hm.arr.length;i++)
		{	if(hm.arr[i]!=null)
			{
				Iterator iter= hm.arr[i].iterator();
				Pair r;
				System.out.print("index "+i+" :[");
				while(iter.hasNext())
				{	
					r=(Pair)iter.next();
					System.out.print(r.value+",");
				}
				System.out.println("]");
			}
			else
			{
				System.out.println("index "+i+": [null]");
			}
		}

	}
	public Vector<String> RemoveDuplicates(Vector<String> Result)
	{
		Vector<String> vec=new Vector<String>();
		MyHashMap rm=new MyHashMap(5113);
		for(int i=0;i<Result.size();i++)
		{
			if(!rm.HaveKey(Result.get(i)))
			{
				vec.add(Result.get(i));
				rm.put(Result.get(i),Result.get(i));
			}

		}
		return vec;
	}
	public void GettingVocablary(String filename) //throws FileNotFoundException
	{
		
		FileInputStream f;
		try
		{
			f=new FileInputStream(filename);
			Scanner r= new Scanner(f);
			int no_of_String=r.nextInt();
			//anagramfunction(no_of_String);
			while(r.hasNextLine())
			{
				FillArr(r.nextLine());
				//System.out.println(r.hasNextLine());
			}
			r.close();			
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not foound");
		}
		


	}
	public String RemoveSpaces(String str)
	{	
		Pattern pattern = Pattern.compile("[ ]+");//This make it even faster
		//String[] s=str.split("[ ]+");
		String[] s=pattern.split(str);
		String st="";
		for(int i=0;i<s.length;i++)
		{
			st+=s[i];
		}
		return st;
	}
	public static void main(String[] args) {
		
		FileInputStream f;
		Vector<String>	print;
		try
		{
			Anagram ob=new Anagram(41113);
			long startTime = System.currentTimeMillis();
			ob.GettingVocablary(args[0]);
			f=new FileInputStream(args[1]);
			Scanner r= new Scanner(f);
			int no_of_Word=Integer.parseInt(r.nextLine());
			//anagramfunction(no_of_String);
			while(r.hasNextLine())
			{	
				//r.nextLine();
				//System.out.println(r.nextLine());
				print=ob.findanagrams(r.nextLine());
				Collections.sort(print);
				for(int i=0;i<print.size();i++)
				{
					System.out.println(print.get(i));
				}
				//System.out.println(print.size());
				System.out.println("-1");
			}
			r.close();		
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			//System.out.println("totalTime :"+totalTime);	
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not foound");
		}			
	}
}
class Pair
{ 
	String key;
	String value;
	public Pair(String key,String value)
	{
		this.key=key;
		this.value=value;
	}
	
}
class MyHashMap//Key and value are both of string type
{
	int size;
	Pair s;
	LinkedList<Pair>[] arr;//It is our Dictionary
	public MyHashMap(int size)
	{
		this.size=size;
		arr=new LinkedList[size];
	}
	public void put(String key,String value)
	{
		s=new Pair(key,value);
		//long indx=HashCode(key)%size;
		int index=HashCode(key);
		if(arr[index]==null)
		{
			arr[index]=new LinkedList<Pair>();
			arr[index].add(s);
		}
		else//collision
		{
			arr[index].add(s);
		}
		
	}
	public int HashCode(String str)
	{	
		int num=0;
		for(int i=0;i<str.length();i++)
		{
			num=(str.charAt(i)+256*num)%size;
		}
		return num;
	}
	public boolean HaveKey(String key)
	{
		//long indx=HashCode(key)%size;
		
		int index=HashCode(key);
		if(arr[index]!=null)
		{
			
			boolean found=false;
			Iterator iter= arr[index].iterator();
			Pair r;
			while(iter.hasNext())
			{	r=(Pair)iter.next();
				if(r.key.equals(key))
				{
					
					found=true;
					return true;
				}
			}
			
		}
		return false;
	}
	public Vector<String> get(String key)//return all the string with same key
	{
		Vector<String> Vals=new Vector<String>();
		//long inex=HashCode(key)%size;
		int index=HashCode(key);
		Iterator iter= arr[index].iterator();
		Pair r;
		while(iter.hasNext())
		{	r=(Pair)iter.next();
			if(r.key.equals(key))
			{
				Vals.add(r.value);
			}
		}
		return Vals;
	}

}
