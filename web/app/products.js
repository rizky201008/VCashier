"use client"
import { useEffect, useState } from "react";
import { CircularProgress, Card, Typography, CardMedia, CardContent, CardActionArea, Box, Autocomplete, TextField } from "@mui/material";
import axios from "axios";
import { useRouter } from 'next/navigation'
 
const Products = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const router = useRouter();

    const getProducts = async () => {
        try {
            setLoading(true);
            setProducts([]);
            const response = await axios.get('https://vc.vixiloc.com/api/products');
            setLoading(false);
            setProducts(response.data.data);
        } catch (error) {
            setLoading(false);
            console.log(error)
        }
    }

    const handleSelect = (_, value) => {
        const selectedProduct = products.find(product => product.name === value);
        if (selectedProduct) {
            router.push(`/product/${selectedProduct.id}`, { scroll: false })
        }
    };

    useEffect(() => {
        getProducts();
    }, []);

    return (
        <div className="tw-flex tw-flex-wrap tw-justify-center">
            <Autocomplete
                freeSolo
                id="free-solo-2-demo"
                disableClearable
                onChange={handleSelect}
                className="tw-w-full tw-mb-3"
                options={products.map((option) => option.name)}
                renderOption={(props, option) => (
                    <li {...props} key={option.id}>
                        {option}
                    </li>
                )}
                renderInput={(params) => (
                    <TextField
                        {...params}
                        label="Cari produk"
                        InputProps={{
                            ...params.InputProps,
                            type: 'search'
                        }}
                    />
                )}
            />
            <Typography gutterBottom variant="h6" component="h2" className="tw-w-full">
                Daftar produk
            </Typography>
            {loading &&
                (
                    <Box display={"flex"} justifyContent={'center'}>
                        <CircularProgress />
                    </Box>
                )
            }
            <div className="tw-grid tw-grid-cols-2 md:tw-grid-cols-3 lg:tw-grid-cols-4 tw-gap-1 lg:tw-gap-2 tw-w-full">
                {products.map((product) => (
                    <Card key={product.id} sx={{ maxWidth: 345 }}>
                        <CardActionArea href={`/product/${product.id}`}>
                            <CardMedia
                                component="img"
                                className="tw-h-[200px] tw-object-cover"
                                image={(product.image_url) ? product.image_url : "https://www.w3schools.com/w3images/lights.jpg"}
                                alt="green iguana"
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h6" component="h3">
                                    {product.name}
                                </Typography>
                                <Typography component="p" variant="body1" color="text.secondary" className="text-description">
                                    {product.description}
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                ))}
            </div>
        </div>
    )
}

export default Products