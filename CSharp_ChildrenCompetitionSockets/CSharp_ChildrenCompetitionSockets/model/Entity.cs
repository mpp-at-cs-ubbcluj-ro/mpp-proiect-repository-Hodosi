using System;

namespace CSharp_ChildrenCompetitionGUI.model
{
    [Serializable]
    public class Entity <ID> : HasId<ID>
    {
        public ID id { get; set; }

        public override string ToString()
        {
            return "Entity { " + "id=" + id + "}";
        }

        public override bool Equals(object obj)
        {
            if (this == obj)
            {
                return true;
            }

            if (!obj.GetType().IsInstanceOfType(typeof(Entity<ID>)))
            {
                return false;
            }
            Entity<ID> entity = (Entity<ID>) obj;
            return this.id.Equals(entity.id);
        }

        public override int GetHashCode()
        {
            return this.id.GetHashCode();
        }
    }
}