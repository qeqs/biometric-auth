package biometric.logic;

//https://github.com/valbok/HEngine/blob/master/core/HEngine.cpp
public class HemmingDistance {

  public static long dist(double left, double right){
    long x = Double.doubleToLongBits(left) ^ Double.doubleToLongBits(right);
    long m1  = 0x5555555555555555L;
    long m2  = 0x3333333333333333L;
    long h01 = 0x0101010101010101L;
    long m4  = 0x0f0f0f0f0f0f0f0fL;

    x -= ( x >> 1L ) & m1;

    x = ( x & m2 ) + ( ( x >> 2 ) & m2 );

    x = ( x + ( x >> 4 ) ) & m4;

    return ( x * h01 ) >> 56;
  }

}
