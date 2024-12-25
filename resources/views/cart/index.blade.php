@extends('layouts.app')

@section('content')
<div class="container mx-auto px-4 py-8">
    <h1 class="text-xl font-semibold mb-8">Zara Store</h1>
    
    <div class="flex items-center justify-between mb-8">
        <div class="text-2xl">Total Price: ETB {{ number_format($total, 2) }}</div>
        <a 
            href="{{ route('cart.checkout') }}" 
            class="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600 transition-colors"
        >
            Check Out
        </a>
    </div>

    <div class="overflow-x-auto">
        <table class="w-full">
            <thead>
                <tr class="border-b">
                    <th class="text-left py-4 px-4 font-medium">Name</th>
                    <th class="text-left py-4 px-4 font-medium">Category</th>
                    <th class="text-left py-4 px-4 font-medium">Price</th>
                    <th class="text-left py-4 px-4 font-medium">Quantity</th>
                </tr>
            </thead>
            <tbody>
                @foreach($cart as $item)
                <tr class="border-b">
                    <td class="py-4 px-4">{{ $item['name'] }}</td>
                    <td class="py-4 px-4">{{ $item['category'] }}</td>
                    <td class="py-4 px-4">${{ number_format($item['price'], 2) }}</td>
                    <td class="py-4 px-4">
                        <input 
                            type="number" 
                            value="{{ $item['quantity'] }}" 
                            min="1"
                            class="w-24 px-3 py-2 bg-gray-100 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                    </td>
                </tr>
                @endforeach
            </tbody>
        </table>
    </div>
</div>
@endsection