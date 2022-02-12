package io.github.angrylid.mall.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.angrylid.mall.dto.PostProductDto;
import io.github.angrylid.mall.generated.entity.Product;
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
        if (dto.getpType().equals("个人闲置")) {
            entity.setpType(true);
        } else if (dto.getpType().equals("我的店铺")) {
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
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    public Product getProduct(String id) {
        return productMapper.selectById(id);
    }

    public Map<String, Object> getProductAndSeller(String id) {
        Map<String, Object> map = new HashMap<>();
        var product = getProduct(id);
        map.put("product", product);

        var sellerId = product.getSellerId();
        var user = userMapper.selectById(sellerId);

        map.put("publisher", user);

        return map;
    }

}
