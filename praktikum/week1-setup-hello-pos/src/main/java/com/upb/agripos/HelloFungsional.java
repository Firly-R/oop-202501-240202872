   import java.util.function.BiConsumer; 
public class HelloFungsional { 
public static void main(String[] args) { BiConsumer<String,Integer> sapa = (nama, NIM) -> System.out.println("Hello, I'm Back " + nama + " NIM " + NIM ); 
sapa.accept("Muhammad Firly Ramadhan", 240202872); 
} 
}
