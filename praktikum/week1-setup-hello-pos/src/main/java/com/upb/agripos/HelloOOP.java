 class Mahasiswa { 
String nama; int NIM; 
Mahasiswa(String n, int u){ nama=n; NIM=u; } 
void sapa(){ System.out.println("Hello, I'm Back " + nama); } 
} 
public class HelloOOP { 
public static void main(String[] args) { 
Mahasiswa m = new Mahasiswa("Muhammad Firly Ramadhan", 240202872); 
m.sapa(); 
} 
} 


