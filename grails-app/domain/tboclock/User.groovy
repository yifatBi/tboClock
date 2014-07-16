package tboclock

import sun.security.util.Password

class User {
    String name
    String password
    static hasMany = [reports: DayReport]
    static constraints = {
       name unique: true, nullable: false
       password nullable: false
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }
}
