
CREATE OR REPLACE FUNCTION select_available_products_sl2425(
    cif VARCHAR,
    categoryId INT
) RETURNS SETOF products AS $$
BEGIN
    RETURN QUERY
        SELECT p.*
          FROM products p
         WHERE p.category_id = categoryId
           AND p.active = TRUE
           AND p.product_id NOT IN ( SELECT sp.product_id
                                       FROM seller_products sp, sellers sell
                                      WHERE sp.seller_id = sell.seller_id
                                        AND  cif1 = sell.cif
                                      );
END;
$$ LANGUAGE plpgsql;


ALTER TABLE IF EXISTS public.sellers
    ADD COLUMN url character varying(255) COLLATE pg_catalog."default";

ALTER TABLE IF EXISTS public.sellers
    ADD COLUMN pro boolean NOT NULL DEFAULT false;

UPDATE sellers
SET pro = true
WHERE seller_id = 1

SELECT * FROM sellers