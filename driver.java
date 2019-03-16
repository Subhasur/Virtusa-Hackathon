import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
class InputReader {
     
    private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;

    public InputReader(InputStream stream) {
        this.stream = stream;
    }

    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }

    public int readInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
    public double readDouble() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        double res = 0;
        while (!isSpaceChar(c) && c != '.') {
            if (c == 'e' || c == 'E') {
                return res * Math.pow(10, readInt());
            }
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res *= 10;
            res += c - '0';
            c = read();
        }
        if (c == '.') {
            c = read();
            double m = 1;
            while (!isSpaceChar(c)) {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, readInt());
                }
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                m /= 10;
                res += (c - '0') * m;
                c = read();
            }
        }
        return res * sgn;
    }
    public long readLong() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        long res = 0;
        do {
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
    public String readString() {//Reads string without any blank spaces in betwn
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }

    public boolean isSpaceChar(int c) {
        if (filter != null)
            return filter.isSpaceChar(c);
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public String next() {
        return readString();
    }

    public interface SpaceCharFilter {
        public boolean isSpaceChar(int ch);
    }
}

class OutputWriter {
    private final PrintWriter writer;

    public OutputWriter(OutputStream outputStream) {
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public OutputWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void print(Object...objects) {//Doesn't print a new line after o/p
        for (int i = 0; i < objects.length; i++) {
            if (i != 0)
                writer.print(' ');
            writer.print(objects[i]);
        }
    }

    public void printLine(Object...objects) {//Prints a new line AFTER printing the o/p
        print(objects);
        writer.println();
    }

    public void close() {//Closes the o/p stream
        writer.close();
    }

    public void flush() {//Flushes the o/p in console
        writer.flush();
    }

    }

class IOUtils {

    public static int[] readIntArray(InputReader in, int size) {//Can read array seperated both by spaces/newlines
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = in.readInt();
        return array;
    }
    public static String[] readStringArray(InputReader in, int size) {//Can read array seperated both by spaces/newlines
        String[] array = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = in.readString();
        return array;
    }

}
class Driver{
    
    public static void main(String[] args)throws IOException,InputMismatchException {
    Driver obj = new Driver();
    //initialize
    InputReader in 		= new InputReader(System.in);
    OutputWriter out	=	new OutputWriter(System.out);
    //database
    ArrayList<Attr> A = new ArrayList<Attr>();
    A.add(new Attr("A","B"));A.add(new Attr("MN","M"));A.add(new Attr("P","X"));
    //for (int i = 0;i<A.size();i++) out.print(A.get(i).getval(1));
    //String ip[] = IOUtils.readStringArray(in, 1);
    obj.recom(A,"A");
    //obj.foo("a","b","c","d");
    out.flush();
}
ArrayList<String> recom( ArrayList<Attr>A, String... attrs){//search on database and recommend
    ArrayList<String> temp = new ArrayList<String>();
    for(String x : attrs){//VERY BAD CODE, OPTIMIZATION NEEDED
        //obj corres to attr 1, then obj corres attr 2
        //ISSUE same obj repeated, FIX
        //System.out.print(x);
        for (int i = 0;i<A.get(0).size();i++){
            temp.add(A.get(0).getval(i));
        }
        for (int i = 0;i<A.get(2).size();i++){
            temp.add(A.get(1).getval(i));
        }
    }
    return temp;
    }
}
class Attr{//Attribute class
    String A, B; //Attributes
    Attr(String x, String y){
        A = x; B = y;
    }
    String getval(int x){//return val based on priority 
        if (x==1)   return A;
        else return B;
    }
}
