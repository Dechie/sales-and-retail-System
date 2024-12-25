<?php
namespace App\Http\Controllers;

use App\Models\Order;
use App\Models\Product;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Session;

class OrderController extends Controller
{
    public function checkout()
    {
        $cart = Session::get('cart', []);
        $user = Auth::user();  // Get the authenticated user

        if ($user && count($cart) > 0) {
            foreach ($cart as $productId => $item) {
                Order::create([
                    'user_id' => $user->id,
                    'product_id' => $productId,
                    'quantity' => $item['quantity']
                ]);
            }


            // Clear the cart after checkout
            Session::forget('cart');

            // Flash success message to the session
            Session::flash('success', 'Your order has been successfully placed!');

            //dd(Session::all());
            return redirect()->route('products.index');
        }

        return redirect()->route('login');  // Redirect to login if not authenticated
    }
}
