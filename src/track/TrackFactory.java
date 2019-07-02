package track;

public class TrackFactory {
  public Track manufacture(int rep) {
    return new Track(rep);
  }
}
