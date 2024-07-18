import ProductDetail from "./product_detail"

const Page = ({ params }) => {
    return (
        <main className="tw-flex tw-min-h-screen tw-flex-col tw-p-5 lg:tw-p-24 tw-bg-gray-100">
            <ProductDetail id={params.id} />
        </main>
    )
}

export default Page