package io.fireant.pplus.database.utility;

import android.os.AsyncTask;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.entities.Category;
import io.fireant.pplus.database.tables.entities.Currency;
import io.fireant.pplus.database.tables.entities.Product;
import io.fireant.pplus.database.tables.entities.Stock;
import io.fireant.pplus.database.dto.StockQuery;
import io.fireant.pplus.database.tables.entities.StockImport;

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void populateWithDefaultData(AppDatabase db) {

        if(db.currencyDao().loadAllCurrency().isEmpty()){
            UUID uuid = UUID.randomUUID();
            Category category = new Category();
            String categoryId = uuid.toString();
            category.id = categoryId;
            category.categoryName = "Test Main Category";
            category.hasChild = 0;
            category.createDate = new Date();
            category.status = 1;
            db.categoryDao().insertCategory(category);
            List<Category> categoryList = db.categoryDao().loadAllCategories();
            System.out.println("Category size:" + categoryList.size());

            Currency currency = new Currency();
            uuid = UUID.randomUUID();
            String currencyId = uuid.toString();
            currency.id = currencyId;
            currency.currencyCode = "KHR";
            db.currencyDao().insertCurrency(currency);
            List<Currency> currencyList = db.currencyDao().loadAllCurrency();
            System.out.println("Currency size:" + currencyList.size());

            currency = new Currency();
            uuid = UUID.randomUUID();
            currencyId = uuid.toString();
            currency.id = currencyId;
            currency.currencyCode = "USD";
            db.currencyDao().insertCurrency(currency);
            currencyList = db.currencyDao().loadAllCurrency();
            System.out.println("Currency size:" + currencyList.size());

            Product product = new Product();
            uuid = UUID.randomUUID();
            String productId = uuid.toString();
            product.id = productId;
            product.productName = "Test Product";
            product.catId = category.id;
            product.code = "0000";
            product.currencyCode = currency.currencyCode;
            product.pricePerUnit = 5D;
            product.createDate = new Date();
            product.status = 1;
            db.productDao().insertProduct(product);
            List<Product> productList = db.productDao().loadAllProduct();
            System.out.println("Product size:" + productList.size());

            Stock stock = new Stock();
            uuid = UUID.randomUUID();
            String stockId = uuid.toString();
            stock.id = stockId;
            stock.proId = product.id;
            stock.totalQuantity = 0;
            stock.createDate = new Date();
            stock.status = 1;
            db.stockDao().insertStock(stock);
            List<StockQuery> stockQueryList = db.stockDao().loadAllStock();
            System.out.println("Stock size:" + stockQueryList.size());

            StockImport stockImport = new StockImport();
            uuid = UUID.randomUUID();
            String stockImportId = uuid.toString();
            stockImport.id = stockImportId;
            stockImport.stockId = stock.id;
            stockImport.quantity = 10;
            stockImport.importDate = new Date();
            stockImport.status = 1;
            db.stockImportDao().insertStockImport(stockImport);

            stock.totalQuantity = 10;
            stock.updateDate = new Date();
            db.stockDao().updateStock(stock);

            stockImport = new StockImport();
            uuid = UUID.randomUUID();
            stockImportId = uuid.toString();
            stockImport.id = stockImportId;
            stockImport.stockId = stock.id;
            stockImport.quantity = 7;
            stockImport.importDate = new Date();
            stockImport.status = 1;
            db.stockImportDao().insertStockImport(stockImport);

            stock.totalQuantity = 17;
            stock.updateDate = new Date();
            db.stockDao().updateStock(stock);

            List<StockImport> stockImportList = db.stockImportDao().loadStockImportByStockId(stock.id);
            System.out.println(stockImportList.size());

        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithDefaultData(mDb);
            return null;
        }

    }
}
