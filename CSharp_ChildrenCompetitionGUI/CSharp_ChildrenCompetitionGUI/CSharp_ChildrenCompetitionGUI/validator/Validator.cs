namespace CSharp_ChildrenCompetition.repository
{
    public interface Validator<E>
    {
        void validate(E entity);
    }
}