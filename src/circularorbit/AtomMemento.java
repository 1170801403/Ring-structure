package circularorbit;

public class AtomMemento {
  private AtomState state;

  public AtomMemento(AtomState state) {
    this.state = state;
  }

  public AtomState getState() {
    return this.state;
  }
}
