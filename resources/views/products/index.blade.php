@extends('layouts.app')

@section('title', 'All Products')

@section('content')
    <div class=" mx-auto my-8">
        <h3 class="text-2xl font-bold mb-4">All Products</h3>
        <section id="Projects"
            class="max-w-[1200px] flex mx-auto grid grid-cols-3 gap-[30px] mt-10 mb-5">
            @foreach ($products as $product)
                <div class=" bg-white shadow-md rounded-xl duration-500 mb-6">
                    <div>
                        <div class="h-[400px]">
                            <img src="{{ URL::asset('images/'.$product->image) }}" class="w-full h-full object-cover rounded-t-xl"> 
                        </div>
                        
                        <div class="px-4 py-3">
                            <span class="text-gray-400 mr-3 uppercase text-xs">Brand</span>
                            <p class="text-lg font-bold text-black truncate block capitalize">{{ $product->name }}</p>
                            <div class="flex items-center">
                                <p class="text-lg font-semibold text-black cursor-auto my-3">{{ $product->price }}</p>
                                <div class="ml-auto">
                                    <a href="{{'/add-to-cart/'.$product->id }}">
                                    <button class="bg-blue-700 hover:bg-transparent hover:text-black hover:outline hover:outline-1 rounded-md p-3 text-white">Add to cart</button>
                                </a>
                                </div>
                               
                            </div>
                                
                        </div>
                    </div>
                </div>
            @endforeach
        </section>
    </div>
@endsection
