package com.example.smartAir.data;
// T is a class representing a piece of data that you want to fetch
// The first method is a piece of code you want to get called when the piece of data is successfully fetched
// The second is what you want to call if it fails for one reason or another
public class DataCallback {

    public interface SingularCallback<T> {
        void onSingleSuccess(T receivedData);

        void onFailure();
    }

    interface ManyCallback<T> {
        void onArraySuccess(T[] receivedData);

        void onFailure();
    }

    interface InsertionCallback<T> {
        void onInsertion(T inserted);

        void onFailure();
    }
}

//// How you should incorporate this data
//class ExampleModelClass {
//
//    public interface ExampleModelClassDatabaseInterface {
//          // Add methods as needed
//          // a "slug" basically just refers to something that can identify an object or a resource
//          // Please use a string or integer
//        void updateDataModel1(anyType insertion, SlugType slug, DataCallback.SingularCallback<anyType> callback); // the type in the callback doesn't matter fo rhtis one
//
//        void fetchDataModel1(SlugType slug, DataCallback.singularCallback<anyType> callback); //
//
//        void updateDataModel2(anyType insertion, SlugType slug, DataCallback<anyType> callback);
//
//        void fetchDataModel2(SlugType slug, DataCallback.SingularCallback<anyType> callback);
//
//        void fetchMultiple(SlugType slug, DataCallback.ManyCallback<Object> callback);
//
//        void fetchDefault(DataCallback.ManyCallback<Object> callback);
//
//        // So on and so forth...
//    }
//
//    // Make sure to include a way of setting the instance
//
//    ExampleModelClassDatabaseInterface db = new ExampleModelClassDatabaseInterface(); // be sure to use a concrete class
//
//
//
//    // Other methods of this class, when needed, will take the instance of the interface above
//    // and then call its methods when needed
//
//    void onDataChanged (Object o) {
//        // implementation blank
//    }
//
//    void onViewCreated () {
//        db.fetchDataModel1("", new DataCallback.SingularCallback<Object>() { // anonymous interface implementation
//           void onSingleSuccess(T receivedData) {
//              view.update(receivedData);
//              this.setSomething(....);
//           }
// .         void onFailure() {} // Leave it blank if you don't want to do anything
//        });

//       button.setOnClickListener(() -> {
    //        db.fetchDataModel2(textValue, new DataCallback.SingularCallback<Object>() { // anonymous interface implementation
    //           void onSingleSuccess(T receivedData) {
    //              view.update(receivedData);
    //              this.setSomething(....);
    //           }
    // .         void onFailure() {
// 
//                  showErrorMessage();
//              } // Leave it blank if you don't want to do anything
    //        });
//       
//       })
//    }
//
// }
//// How the db accessor is going to look:
//class ExampleDatabaseAccessor implements ExampleModelClass.ExampleModelDatabaseAccessor {
//        void updateDataModel1(anyType insertion, anyType slug, DataCallback.SingularCallback<anyType> callback) {
//
//          db.fetch(......); //
//          callback.onSuccess(.....) // When the db gets the data is calls the function you pass in the callback
//
//        } // the type in the callback doesn't matter fo rhtis one
//
//
// }
//
//// Creating a mock class to test
// class ExampleMockDatabaseAccessor implements ExampleModelClass.ExampleModelClassDatabaseInterface {
//    // implement the methods
//    Object single;
//    HashMap<SlugType, Object> array;
//
//    void fetchDataModel2(SlugType slug, DataCallback.SingularCallback<Object> callback) {
//        callback.onSingleSuccess(array.get(slug)));
//    }
//      // Implement other methods
//    void fetchMultiple(SlugType slug, DataCallback.ManyCallback<Object> callback) {
//          callback.onArraySuccess(array);
//    }
 //    //s o on and so forth
 //
// }
