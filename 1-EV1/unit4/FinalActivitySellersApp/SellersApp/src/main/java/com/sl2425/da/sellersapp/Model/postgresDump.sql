

CREATE OR REPLACE FUNCTION select_available_products_sl2425(
    sellerId INT,
    categoryId INT
) RETURNS SETOF products AS $$
BEGIN
    RETURN QUERY
        SELECT p.*
        FROM products p
        WHERE p.category_id = categoryId
          AND p.active = TRUE
          AND p.product_id NOT IN ( SELECT sp.product_id
                                      FROM seller_products sp
                                     WHERE sp.seller_id = sellerId
        );
END;
$$ LANGUAGE plpgsql;
