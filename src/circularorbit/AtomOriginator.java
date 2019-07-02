package circularorbit;

public class AtomOriginator {
  private AtomState state;

  public void setState(AtomState state) {
    this.state = state;
  }

  public AtomMemento addMemento() {
    return new AtomMemento(this.state);
  }

  public void restore(AtomMemento m) {
    this.state = m.getState();
  }
}
