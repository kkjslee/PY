package ibilling

/**
 * FilterSet
 */
class FilterSet implements Serializable {

    static mapping = {
        id generator: 'org.hibernate.id.enhanced.TableGenerator',
            params: [
                table_name: 'ibilling_seqs',
                segment_column_name: 'name',
                value_column_name: 'next_id',
                segment_value: 'filter_set'
            ]
        filters cascade: "all,delete-orphan"
    }

    static hasMany = [filters: Filter]   

    String name
    Integer userId

    public String toString ( ) {
        return "FilterSet{id=${id}, name=${name}, userId=${userId}, filters=${filters?.size()}}"
    }
}
