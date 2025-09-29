package Model;


/**
 * This is a Java record, meaning it is an immutable data holder.
 * It stores details such as the client's name, the product purchased,the quantity, and the total price of the order.
 */
public record  Bill(int id, String clientName, String productName, int quantity, int totalPrice) {
}