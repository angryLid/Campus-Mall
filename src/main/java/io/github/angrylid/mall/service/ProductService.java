package io.github.angrylid.mall.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.angrylid.mall.dto.PostProductDto;
import io.github.angrylid.mall.generated.entity.Favorite;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.mapper.FavoriteMapper;
import io.github.angrylid.mall.generated.mapper.ProductMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;
import io.github.angrylid.mall.mapper.ProductDetailMapper;
import io.github.angrylid.mall.utils.Minio;

@Service
public class ProductService {

    @Resource
    ProductDetailMapper myProductMapper;

    @Resource
    ProductMapper productMapper;

    @Resource
    UserMapper userMapper;

    @Autowired
    Minio minio;

    @Autowired
    private FavoriteMapper favoriteMapper;

    /**
     * 插入一条产品记录
     * 
     * @param dto
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void addProduct(PostProductDto dto, Integer id) throws IllegalAccessException, IOException {
        var entity = new Product();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setSellerId(id);
        entity.setPrice(new BigDecimal(dto.getPrice()));
        if (dto.getpType().equals("personal")) {
            entity.setpType(true);
        } else if (dto.getpType().equals("enterprise")) {
            entity.setpType(false);
        } else {
            entity.setpType(null);
        }

        for (MultipartFile file = dto.getImage0(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage0(name);
        }
        for (MultipartFile file = dto.getImage1(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage1(name);
        }
        for (MultipartFile file = dto.getImage2(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage2(name);
        }
        for (MultipartFile file = dto.getImage3(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage3(name);
        }
        for (MultipartFile file = dto.getImage4(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage4(name);
        }
        for (MultipartFile file = dto.getImage5(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage5(name);
        }

        try {
            productMapper.insert(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 检索所有店家的商品
     * 
     * @return 店家商品列表
     */
    public List<Product> selectRtrProducts() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_type", 0);
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 检索所有店家的商品 排除自己的商品
     * 
     * @param id
     * @return
     */
    public List<Product> selectRtrProducts(Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("seller_id", id);

        queryWrapper.eq("p_type", 0);
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 检索所有个人的商品
     * 
     * @return 商品列表
     */
    public List<Product> selectUserProducts() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_type", 1);
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 检索所有商品 排除用户自己的商品
     * 
     * @param id
     * @return
     */
    public List<Product> selectUserProducts(Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("seller_id", id);
        queryWrapper.eq("p_type", 1);
        queryWrapper.orderByDesc("created_at");
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 检索商品发布者的联系方式
     * 
     * @param productId
     * @return
     */
    public String selectSellerTel(Integer productId) {
        Product product = productMapper.selectById(productId);
        Integer sellerId = product.getSellerId();
        String tel = userMapper.selectById(sellerId).getTelephone();
        return tel;
    }

    public Product getProduct(String id) {
        return productMapper.selectById(id);
    }

    public Map<String, Object> getProductAndSeller(String id) {
        Map<String, Object> map = new HashMap<>();
        var product = getProduct(id);
        var sellerId = product.getSellerId();
        var user = userMapper.selectById(sellerId);

        map.put("sellerName", user.getNickname());
        map.put("sellerTel", user.getTelephone());
        map.put("publishTime", product.getCreatedAt().toString());
        map.put("title", product.getTitle());
        map.put("price", product.getPrice().toString());
        map.put("description", product.getDescription());

        map.put("image0", product.getImage0());
        map.put("image1", product.getImage1());
        map.put("image2", product.getImage2());
        map.put("image3", product.getImage3());
        map.put("image4", product.getImage4());
        map.put("image5", product.getImage5());

        return map;
    }

    public Map<String, Object> getProductAndSeller(Integer userId, String productId) {
        Map<String, Object> map = getProductAndSeller(productId);
        Favorite favorite = favoriteMapper
                .selectOne(new QueryWrapper<Favorite>().eq("user_id", userId).eq("product_id", productId));
        if (favorite != null) {
            map.put("favorate", true);
        } else {
            map.put("favorate", false);
        }
        return map;
    }

    public List<Product> selectMyPublished(Integer id) {
        return productMapper.selectList(new QueryWrapper<Product>().eq("seller_id", id));
    }

    public Integer deleteProduct(Integer id) {
        return productMapper.deleteById(id);
    }

    public List<Product> selectFavorite(Integer id) {
        List<Favorite> favorites = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("user_id", id));
        List<Product> products = new ArrayList<>();
        for (Favorite favorite : favorites) {
            products.add(productMapper.selectById(favorite.getProductId()));
        }
        return products;
    }

    public Integer addFavorite(Integer userId, Integer productId) {
        return 1;
    }

}
