public class RiceFactory implements IFactory{
    @Override
    public FoodStuff produceFoodStuff() {
        return new Rice();
    }
}
