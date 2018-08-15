public class MilletFactory implements IFactory{
    @Override
    public FoodStuff produceFoodStuff() {
        return new Millet();
    }
}
