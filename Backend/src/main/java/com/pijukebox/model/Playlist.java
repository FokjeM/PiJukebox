package com.pijukebox.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Entity
@AllArgsConstructor
@Table(schema = "pijukebox", name = "playlist")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Playlist extends BeanUtils {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private String description;

    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
    @JoinTable
    @OneToMany
    private List<Track> tracks;

    // https://codippa.com/skip-null-properties-spring-beanutils/

    public void CopyNonNullProperties(Playlist target) throws Exception {
        BeanUtils.copyProperties(this, target, getNullProperties());
    }

    private String[] getNullProperties() throws Exception {
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(this.getClass(), Object.class).getPropertyDescriptors();
        return Arrays.stream(propDescArr).filter(getNullPredicate()).map(PropertyDescriptor::getName).toArray(String[]::new);
    }

    private Predicate<PropertyDescriptor> getNullPredicate() {
        return pd -> {
            boolean result = false;
            try {
                Method getterMethod = pd.getReadMethod();
                result = (getterMethod != null && getterMethod.invoke(this) == null);
            } catch (Exception e) {
                LoggerFactory.getLogger(this.getClass()).error("Error invoking getter method");
            }
            return result;
        };
    }
}
