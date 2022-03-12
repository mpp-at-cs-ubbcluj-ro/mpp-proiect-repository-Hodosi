package model;


import java.util.Objects;

public class Entity<ID> implements HasId<ID> {
    protected ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Entity)){
            return false;
        }
        Entity<?> entity = (Entity<?>) o;
        return this.getId().equals(entity.getId());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
