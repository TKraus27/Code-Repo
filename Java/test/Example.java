class Example {
  static int x;
  int y;
  public int doSomething() {
    int z = this.y + x;
    return z;
  }
  public String getString() {
    return new String("Hello world " + y);
  }
  public static void main(String[] args) {
    Example e = new Example();
    System.out.println(e.getString());
    int a = e.doSomething();
  }
}
