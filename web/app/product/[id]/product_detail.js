"use client"
import { useEffect, useState } from "react"
import { Container, Typography, Table, TableBody, TableCell, TableContainer, TableRow, Paper, TableHead, CircularProgress, Box, AppBar, Toolbar, IconButton, MenuIcon, Button } from "@mui/material"
import axios from "axios"
const ProductDetail = ({ id }) => {

    const [product, setProduct] = useState({});
    const [loading, setLoading] = useState(false);

    const getProduct = async (productId) => {
        try {
            setLoading(true)
            setProduct({})
            const response = await axios.get('https://vc.vixiloc.com/api/products/' + productId)
            const data = response.data
            setProduct(data)
            setLoading(false)
        } catch (error) {
            setLoading(false)
            console.log(error)
        }
    }

    useEffect(() => {
        getProduct(id)
    }, [])

    return (
        <div className="tw-flex tw-flex-col tw-justify-center">
            {loading && (
                <Box display={"flex"} justifyContent={'center'} width={"100%"}>
                    <CircularProgress />
                </Box>
            )}
            {!loading && (
                <div className="tw-flex tw-flex-col tw-justify-center lg:tw-flex-row">
                    <img src={(product.image_url) ? product.image_url : "https://cdn.pixabay.com/photo/2023/08/19/13/42/flowers-8200510_1280.jpg"} className="tw-w-full lg:tw-w-1/2 tw-object-cover tw-max-h-[500px]" />
                    <Container>
                        <Typography variant="h4" component={"h2"} >
                            {product.name}
                        </Typography>
                        <Typography variant="p" component={"p"} className="tw-mb-3">
                            {product.description}
                        </Typography>

                        <Paper sx={{ width: '100%', overflow: 'hidden' }} className="tw-mb-3">
                            <TableContainer sx={{ maxHeight: 300 }}>
                                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>Variasi Produk</TableCell>
                                            <TableCell align="right">Harga</TableCell>
                                            <TableCell align="right">Harga grosir</TableCell>
                                            <TableCell align="right">Stok</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {product.variations && product.variations.map((variation) => (
                                            <TableRow
                                                key={variation.id}
                                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                            >
                                                <TableCell component="th" scope="row">
                                                    {variation.unit}
                                                </TableCell>
                                                <TableCell align="right">{variation.price}</TableCell>
                                                <TableCell align="right">{variation.price_grocery}</TableCell>
                                                <TableCell align="right">{variation.stock}</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </Paper>
                        <Button variant="contained" className="tw-w-full" href={"https://wa.me/6285316969283?text=Saya ingin memesan produk " + product.name}>
                            Pesan Sekarang
                        </Button>
                    </Container>
                </div>
            )}
        </div>
    )
}

export default ProductDetail