<?php
namespace App\Http\Controllers;

use App\Models\Cart;
use App\Models\Product;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Session;

class CartController extends Controller
{
    public function index()
    {
        $cart = Session::get('cart', []);  // Get cart from session, default to an empty array
        $total = 0;

        foreach ($cart as $item) {
            $total += $item['price'] * $item['quantity'];
        }

        return view('cart.index', compact('cart', 'total'));  // Pass cart and total to view
    }

    public function addToCart($id)
    {
        $product = Product::findOrFail($id);  // Find the product by ID
        $cart = Session::get('cart', []);

        // Check if the product is already in the cart
        if (isset($cart[$id])) {
            $cart[$id]['quantity']++;
        } else {
            $cart[$id] = [
                'name' => $product->name,
                'category' => $product->category,
                'price' => $product->price,
                'quantity' => 1
            ];
        }

        Session::put('cart', $cart);  // Save the cart in the session
        return redirect()->route('cart.index');  // Redirect to the cart page
    }
}
