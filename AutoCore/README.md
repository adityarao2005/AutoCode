# AutoCode.AutoCore

The Auto Code's Auto Core framework provides basic data structures, algorithms, and utilities that would be useful for project development as for other requirements for future API in the Auto Code framework.

This framework will have basic utilities:
 - Basic Design patterns and interfaces
 - Wrapping IO
 - Basic Reflection API
 - Creating Properties and Pseudo-Objects
 - Creating Object Templates/Builders
 - And more to come

All Data structures will be in the **com.raos.autocode.core.ds** package
 - These will be concrete classes which implement/extend certain required algorithms

All Algorithms will be in the **com.raos.autocode.core.algorithm** package
 - These will either be abstract classes or interfaces which provides the utility
 
All Design Patterns will be in the **com.raos.autocode.core.design** package

Current version where we are at:

 - Basic Design patterns and interfaces have been implemented
 	- Extensive builder pattern implementation
 	- Builder Factory implementation
 	- State Pattern implementation
 	- More implementations required
 - Wrapping IO (Not implemented)(idk why i added this feature)
 - Basic Reflection API (not implemented yet)
 - Added debugging support/encapsulation of logging
 - **Added Some Beans Support**
    - **Properties Support**
        - Properties basic flavours defined, concrete implementation not defined
        - Binding not defined
        - PropertyManager interface defined
        - PropertyChangeEvent and API defined
    - **Beans Processing**
       - Pre and Post processing interfaces defined, concrete implementations not defined
       - Bean Factory, BeanManager, BeanDelegate, BeanClass - not defined
       - Java Beans wrapping for beans
    - **Context and Dependency Injection not defined**
 - Not added PseudoObjects/Prototype Objects yet
 - Many Utility classes created

One of the goals, redesign how we think of data. Observe:
```java
public interface User {
	Property<String> name();
	Property<Date> dob();
	ListProperty<Address> addresses();
	MapProperty<SocialPlatform, String> handles();

	default ReadOnlyProperty<Integer> age() {
		return dob().view((date) => {
			return Date.now().getYear() - date.getYear();
		});
	}
}
```
Remove boilerplate for classes meant only for data and potentially support easier persistence and observability.
